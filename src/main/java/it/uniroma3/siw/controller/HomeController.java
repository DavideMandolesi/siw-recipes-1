package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.service.UserService;

@Controller
class HomeController {
	@Autowired UserService userService;
	@Autowired RecipeService recipeService;

	@GetMapping("/")
	public String home(Model model){
		model.addAttribute("isLogged",userService.isLogged());
		model.addAttribute("isAdmin",userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
		model.addAttribute("defaultRecipeUrlImage", Recipe.DEFAULT_URL_RECIPE_IMG_);
		
		model.addAttribute("latestRecipes", recipeService.getLatest4Recipes());
		
		return "home";
	}
	
	@GetMapping("/error")
	public String error(){
		return "error";
	}
}