package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class RecipeController {
	@Autowired UserService userService;
	@Autowired RecipeService recipeService;
	
	@GetMapping("/recipe")
	public String getRecipe() {
		return "recipe";
	}
	
	@GetMapping("/recipeList")
	public String getRecipeList() {
		return "recipeList";
	}
	
	@GetMapping("/formNewRecipe")
	public String formNewRecipe(@ModelAttribute("recipe") Recipe recipe,Model model) {
		model.addAttribute("isLogged",userService.isLogged());
		model.addAttribute("isAdmin",userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
		
		if(recipe!=null) {
			model.addAttribute(recipe);
		}else {model.addAttribute("recipe", new Recipe());}
		
		return "formNewRecipe";
	}
	
	
	@PostMapping("/confirmNewRecipe")
	@Transactional
	public String confirmNewRecipe(@Valid @ModelAttribute("recipe") Recipe recipe,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "formNewRecipe";
	    }
		
		recipe.setAuthor(userService.getCurrentUser());
		recipe.setCreationDate(LocalDate.now(ZoneId.of("GMT")));
		recipe.setIngredients((List<Ingredient>)new ArrayList<Ingredient>());//non so se è corretto?
		recipe.setReviews((List<Review>)new ArrayList<Review>());//non so se è corretto?
		recipeService.save(recipe);
		
		return "redirect:/";
	}
}
