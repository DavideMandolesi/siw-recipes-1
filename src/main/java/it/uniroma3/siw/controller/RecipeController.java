package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RecipeController {
	
	@GetMapping("/recipe")
	public String getRecipe() {
		return "recipe";
	}
	
	@GetMapping("/recipeList")
	public String getRecipeList() {
		return "recipeList";
	}
	
	@GetMapping("/newRecipe")
	public String formNewRecipe() {
		return "fromNewRecipe";
	}
	@PostMapping("/confirmNewRecipe")
	public String confirmNewRecipe() {
		return "recipe";
	}
}
