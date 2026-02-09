package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired UserRepository userRepository;
	@Autowired CredentialsService credentialsService;
	@Autowired MessageSource messageSource;

	@Transactional
	public void registerNewUser (User user, Credentials credentials)throws IllegalArgumentException {
		if(credentialsService.existsByUsername(credentials.getUsername())){
			throw new IllegalArgumentException(messageSource.getMessage("error.user.usernameAlreadyExists",null, LocaleContextHolder.getLocale()));
		}
		if(existsByEmail(user.getEmail())){
			throw new IllegalArgumentException(messageSource.getMessage("error.user.emailAlreadyExists",null, LocaleContextHolder.getLocale()));
		}
		
		//non è duplicato
		save(user);
		
		credentials.setUser(user);
		credentials.setRole(Credentials.DEFAULT_ROLE);
		credentialsService.save(credentials);
		
		user.setCredentials(credentials);
		user.setUrlImage(User.DEFAULT_URL_PROFILE_PIC);
		user.setIsBanned(false);
		save(user);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	/**
     * Recupera l'entità User corrispondente all'utente attualmente autenticato.
     * @return l'entità User, o null se nessun utente è autenticato.
     */
    @Transactional
    public User getCurrentUser() {
        // L'oggetto 'principal' contiene i dettagli dell'utente loggato.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            // Usiamo il repository per recuperare la nostra entità completa dal database.
            return userRepository.findByCredentialsUsername(username).orElse(null);
        } else if (principal instanceof String) {
            // In alcuni casi (es. autenticazione OAuth2), il principal potrebbe essere solo una stringa.
            String username = (String) principal;
            return userRepository.findByCredentialsUsername(username).orElse(null);
        } else {
            return null;
        }
    }
    
	public boolean isLogged() {
	    //se c'è un utente corrente -> l'utente è loggato
		if(getCurrentUser()!=null) return true;
		return false;
	}
	
	public boolean isAdmin() {
		User currentUser =getCurrentUser(); 
		if(currentUser!=null) {
			return currentUser.getCredentials().getRole().equals(Credentials.ADMIN_ROLE);
		}
		//se l'utente non è loggato sicurametne non è admin
		return false;
	}

	public List<User> getAllUsers() {
		return (List<User>)userRepository.findAll();
	}
	
	public List<User> searchUsers(String name) {
		// Se i parametri sono stringhe vuote, li tratto come null	    
		String searchParam = null;
	    if (name != null && !name.isBlank()) {
	        // Prepara la stringa lowercase compatibile alla ricerca parziale: "Pasta" diventa "%pasta%"
	        searchParam = "%" + name.toLowerCase() + "%";
	    }
	    if(name != null && name.isBlank()) {searchParam=null;}
	    
		return userRepository.findBySearchParam(searchParam);
	}
	
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public void banUser(User user) {
		user.setIsBanned(true);
	}
	public void unbanUser(User user) {
		user.setIsBanned(false);
	}

	public void updateUrlImage(User currentUser, String newUrl) {
		if(newUrl!=null && !newUrl.isBlank()) {
			currentUser.setUrlImage(newUrl);
		}else {
			currentUser.setUrlImage(User.DEFAULT_URL_PROFILE_PIC);
		}
		save(currentUser);
	}
	
}
