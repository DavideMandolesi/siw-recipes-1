package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;

@Controller
public class ReviewController {
	@Autowired UserService userService;
	@Autowired RecipeService recipeService;
	@Autowired ReviewService reviewService;
	
	@GetMapping("/formNewReview/{id}")
	public String formNewReview(@PathVariable("id") Long id,
			Model model) {
		model.addAttribute("isLogged",userService.isLogged());
		model.addAttribute("isAdmin",userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
		model.addAttribute("defaultRecipeUrlImage", Recipe.DEFAULT_URL_RECIPE_IMG_);
		
		Recipe recipe = recipeService.findRecipeById(id);
		if(recipe!=null) {
			model.addAttribute("review", new Review());
			model.addAttribute("recipe",recipe);
			return "formNewReview";
		}
		return"redirect:/home";
	}
	
	@PostMapping("/confirmReviewCreation/{recipeId}")
	public String confirmReviewCreation(@ModelAttribute("review") Review review,
			@PathVariable("recipeId") Long recipeId,
			Model model) {
		
		Recipe recipe = recipeService.findRecipeById(recipeId);
		
		if(review!=null && recipe!=null) {

			review.setCreationDate(LocalDate.now(ZoneId.of("GMT")));
			review.setRecipe(recipe);
			review.setAuthor(userService.getCurrentUser());
			reviewService.save(review);
			
			//recipe.getReviews().add(review);
			//recipeService.save(recipe);
			
			return"redirect:/recipe/"+recipe.getId();
		}
		else return "redirect:/home";
	}
}
