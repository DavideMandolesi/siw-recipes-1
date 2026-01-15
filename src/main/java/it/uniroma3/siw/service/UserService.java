package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
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
		user.setIsBanned(false);
		save(user);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
}
