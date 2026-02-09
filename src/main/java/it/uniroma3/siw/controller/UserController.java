package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {
	
	@Autowired private MessageSource messageSource;
	@Autowired private UserService userService;
	
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/formNewUser")
	public String formNewUser(Model model) {
		model.addAttribute("user", new User()); 
	    model.addAttribute("credentials", new Credentials());
		return "signup";
	}
	
	@PostMapping("/register")
	public String register(Model model,
			@ModelAttribute("user") User user,
			@ModelAttribute("credentials") Credentials credentials,
			@RequestParam("confirmPassword") String confirmPassword) {
		
		/**
		 * 1: controlla che non esista già l'utente (username o email).
		 * 2: match conferma password e pw?
		 * 3: salva
		 */
		
		if(!confirmPassword.equals(credentials.getPassword())) {
			String errorMsg = messageSource.getMessage("error.password.dismatch", null, LocaleContextHolder.getLocale());
			model.addAttribute("errorMessage", errorMsg);
			return "signup";
		}
		
		try {
			userService.registerNewUser(user,credentials);
		} catch (Exception e) {
			model.addAttribute("errorMessage",e.getMessage());
			return "signup";
		}
		
		
		return "home";
	}
	
	
	@GetMapping("/profile")
	public String profile(Model model) {
		return "profile";
	}
	
	@PostMapping("/updateProfileImage")
	public String updateProfileImage(@RequestParam("urlImage") String newUrl,
			Model model) {
		User currentUser = userService.getCurrentUser();
		//si occupa anche di salvare l'user
		userService.updateUrlImage(currentUser,newUrl);
		return"redirect:/profile";
	}
}
