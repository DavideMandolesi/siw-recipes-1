package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;

@ControllerAdvice
public class GlobalControllerAdvice {
	@Autowired
    private UserService userService;
	
	@ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("isLogged", userService.isLogged());
        model.addAttribute("isAdmin", userService.isAdmin());
        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
        model.addAttribute("defaultRecipeUrlImage", Recipe.DEFAULT_URL_RECIPE_IMG_);
    }
}
