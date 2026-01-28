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
	@Autowired
	UserService userService;
	@Autowired
	RecipeService recipeService;
	@Autowired
	ReviewService reviewService;

	@GetMapping("/formNewReview/{id}")
	public String formNewReview(@PathVariable("id") Long id, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		model.addAttribute("isLogged", userService.isLogged());
		model.addAttribute("isAdmin", userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
		model.addAttribute("defaultRecipeUrlImage", Recipe.DEFAULT_URL_RECIPE_IMG_);

		Recipe recipe = recipeService.findRecipeById(id);
		if (recipe != null) {
			model.addAttribute("review", new Review());
			model.addAttribute("recipe", recipe);
			return "formNewReview";
		}
		return "redirect:/";
	}

	@PostMapping("/confirmReviewCreation/{recipeId}")
	public String confirmReviewCreation(@ModelAttribute("review") Review review,
			@PathVariable("recipeId") Long recipeId, Model model) {
		
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		
		Recipe recipe = recipeService.findRecipeById(recipeId);

		if (review != null && recipe != null) {

			review.setCreationDate(LocalDate.now(ZoneId.of("GMT")));
			review.setRecipe(recipe);
			review.setAuthor(userService.getCurrentUser());
			reviewService.save(review);

			return "redirect:/recipe/" + recipeId;
		} else
			return "redirect:/";
	}

	@GetMapping("/editReview/{reviewId}")
	public String editReview(@PathVariable("reviewId") Long reviewId, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		Review review = reviewService.findReviewById(reviewId);
		if (review != null) {
			if (!(userService.getCurrentUser().getId() == review.getAuthor().getId() || userService.isAdmin()))
				return "redirect:/recipe/" + review.getRecipe().getId();
			model.addAttribute("isLogged", userService.isLogged());
			model.addAttribute("isAdmin", userService.isAdmin());
			model.addAttribute("currentUser", userService.getCurrentUser());
			model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
			model.addAttribute("defaultRecipeUrlImage", Recipe.DEFAULT_URL_RECIPE_IMG_);

			model.addAttribute("review", review);
			model.addAttribute("recipe", review.getRecipe());
			return "formEditReview";
		}
		return "redirect:/";
	}

	@PostMapping("/confirmReviewEdit/{reviewId}")
	public String confirmReviewEdit(@PathVariable("reviewId") Long reviewId, @ModelAttribute("review") Review review,
			Model model) {

		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		Review reviewDB = reviewService.findReviewById(reviewId);
		if (reviewDB != null) {
			if (!(userService.getCurrentUser().getId() == reviewDB.getAuthor().getId() || userService.isAdmin()))
				return "redirect:/recipe/" + reviewDB.getRecipe().getId();

			reviewDB.setRating(review.getRating());
			reviewDB.setText(review.getText());
			reviewService.save(reviewDB);
			return "redirect:/recipe/" + reviewDB.getRecipe().getId();
		}
		return "redirect:/";
	}

	@GetMapping("/deleteReview/{reviewId}")
	public String deleteReview(@PathVariable("reviewId") Long reviewId, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		Review review = reviewService.findReviewById(reviewId);
		if (review != null) {
			if (!(userService.getCurrentUser().getId() == review.getAuthor().getId() || userService.isAdmin()))
				return "redirect:/recipe/" + review.getRecipe().getId();
			reviewService.deleteReview(review);
			return "redirect:/recipe/" + review.getRecipe().getId();
		}
		return "redirect:/";
	}

}
