package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CategoryService;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class RecipeController {
	@Autowired
	UserService userService;
	@Autowired
	RecipeService recipeService;
	@Autowired
	CategoryService categoryService;

	/*
	 * VISUALIZZAZIONE
	 */
	@GetMapping("/recipe/{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("isLogged", userService.isLogged());
		model.addAttribute("isAdmin", userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
		model.addAttribute("defaultRecipeUrlImage", Recipe.DEFAULT_URL_RECIPE_IMG_);

		Recipe recipe = recipeService.findRecipeById(id);
		if (recipe != null) {
			model.addAttribute("recipe", recipe);
			return "recipe";
		}
		// aggiungi errore ricetta non presente
		return "redirect:/";
	}

	@GetMapping("/recipeList")
	public String getRecipeList(@RequestParam(required = false) String title,
			@RequestParam(required = false) Long categoryId, @RequestParam(required = false) String difficulty,
			Model model) {
		model.addAttribute("isLogged", userService.isLogged());
		model.addAttribute("isAdmin", userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
		model.addAttribute("defaultRecipeUrlImage", Recipe.DEFAULT_URL_RECIPE_IMG_);

		List<Recipe> recipes = recipeService.searchRecipes(title, categoryId, difficulty);

		model.addAttribute("queryRecipes", recipes);
		model.addAttribute("categories", categoryService.getAllCategories());

		return "recipeList";
	}
	
	@GetMapping("/myRecipeList")
	public String getMyRecipeList(/*@RequestParam(required = false) String title,
			@RequestParam(required = false) Long categoryId, @RequestParam(required = false) String difficulty,*/
			Model model) {
		model.addAttribute("isLogged", userService.isLogged());
		model.addAttribute("isAdmin", userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
		model.addAttribute("defaultRecipeUrlImage", Recipe.DEFAULT_URL_RECIPE_IMG_);

		// AuthConfiguration garantisce che currentUser !=null
		List<Recipe> recipes = userService.getCurrentUser().getRecipes();

		model.addAttribute("recipes", recipes);

		return "myRecipeList";
	}

	/*
	 * CREAZIONE RICETTA
	 */
	@GetMapping("/formNewRecipe")
	public String formNewRecipe(@ModelAttribute("recipe") Recipe recipe, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}

		/* -- INFO NECESSARIE PER SIDEBAR -- */
		model.addAttribute("isLogged", userService.isLogged());
		model.addAttribute("isAdmin", userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);

		model.addAttribute("categoryList", categoryService.getAllCategories());

		if (recipe != null) {
			model.addAttribute(recipe);
		} else {
			model.addAttribute("recipe", new Recipe());
		}

		return "formNewRecipe";
	}

	@PostMapping("/confirmNewRecipe")
	@Transactional
	public String confirmNewRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		if (bindingResult.hasErrors()) {
			return "formNewRecipe";
		}

		recipe.setAuthor(userService.getCurrentUser());
		recipe.setCreationDate(LocalDate.now(ZoneId.of("GMT")));
		recipe.setReviews((List<Review>) new ArrayList<Review>());// non so se è corretto?
		// imposta flag temporaneo=true
		recipeService.save(recipe);

		return "redirect:/formNewIngredient/" + recipe.getId();
	}

	@GetMapping("/formNewIngredient/{id}")
	public String formNewIngredient(@PathVariable("id") Long id, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		// qui dentro non si può refreshare la pagina altrimenti si perde il riferimento
		// a recipeId (rimane in sessione solo
		// per un passaggio visto che è passato da FlashAttribute. Quando model prova
		// ricostruirlo non trova il costruttore Long

		/* -- INFO NECESSARIE PER SIDEBAR -- */
		model.addAttribute("isLogged", userService.isLogged());
		model.addAttribute("isAdmin", userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);

		// Se l'ID non è stato passato (es. accesso diretto all'URL), reindirizza alla
		// creazione
		if (id == null) {
			// String errorMsg = messageSource.getMessage("error.recipe.null_or_invalid_id",
			// null, LocaleContextHolder.getLocale());
			// model.addAttribute("errorMessage", errorMsg);
			// attenzione, questo tipo di messaggio non viene mostrato nella pagina
			// formNewRecipe.html, per farlo aggiungi visualizzazione
			return "redirect:/formNewRecipe";
		}

		Optional<Recipe> recipe = recipeService.findById(id);
		// non è stato trovato alcuna ricetta con quell'id
		if (recipe.isEmpty()) {
			// String errorMsg = messageSource.getMessage("error.recipe.null_or_invalid_id",
			// null, LocaleContextHolder.getLocale());
			// model.addAttribute("errorMessage", errorMsg);
			// attenzione, questo tipo di messaggio non viene mostrato nella pagina
			// formNewRecipe.html, per farlo aggiungi visualizzazione
			return "redirect:/formNewRecipe";
		}

		// qui hai l'oggetto recipe caricato correttamente
		Recipe r = recipe.get();
		model.addAttribute(r);
		model.addAttribute("ingIndex", recipeService.getLastEmptyIngredientIndex(r));
		return "formNewIngredient";
	}

	@PostMapping("/confirmNewIngredient/{id}")
	@Transactional
	public String confirmNewIngredient(@ModelAttribute("recipe") Recipe recipe, // Recupera dal form
			@PathVariable("id") Long id, @RequestParam("ingIndex") int index, // Passiamo l'indice
			RedirectAttributes redirectAttributes, Model model) {

		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		// questo è l'oggetto con tutte le info tranne l'utlimo ingrediente
		Recipe recipeDB = recipeService.findRecipeById(id);
		Ingredient nuovoIng = recipe.getIngredients().get(index);
		if (nuovoIng.getName() != null && !nuovoIng.getName().isBlank()) {
			recipeDB.getIngredients().add(nuovoIng);
			recipeService.save(recipeDB); // Salva l'aggiunta
		}

		return "redirect:/formNewIngredient/" + id;
	}

	@GetMapping("/confirmRecipe/{id}")
	@Transactional
	public String confirmRecipe(@PathVariable("id") Long id) {

		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		recipeService.findById(id).ifPresent(r -> {
			recipeService.setActive(r);
			r.setCreationDate(LocalDate.now(ZoneId.of("GMT")));
			recipeService.save(r);
		});
		return "redirect:/recipe/" + id;
	}

	/*
	 * MODIFICA RICETTA
	 */
	@GetMapping("/editRecipe/{id}")
	public String editRecipe(@PathVariable Long id, @ModelAttribute("recipe") Recipe recipe, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		model.addAttribute("isLogged", userService.isLogged());
		model.addAttribute("isAdmin", userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);

		model.addAttribute("categoryList", categoryService.getAllCategories());
		Recipe r = recipeService.findRecipeById(id);

		if (r != null) {
			if (!(r.getAuthor().getId() == userService.getCurrentUser().getId()))
				return "redirect:/recipe/" + r.getId();
			model.addAttribute("recipe", r);
			return "formEditRecipe";
		}
		return "redirect:/";
	}

	@PostMapping("/confirmEditRecipe/{recipeId}")
	@Transactional
	public String confirmEditRecipe(@Valid @ModelAttribute("recipe") Recipe recipe,
			@PathVariable("recipeId") Long recipeId, BindingResult bindingResult) {

		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		if (bindingResult.hasErrors()) {
			return "redirect:/formEditRecipe/" + recipeId;
		}

		Recipe recipeDB = recipeService.findRecipeById(recipeId);
		if (recipeDB != null) {
			if (!(recipeDB.getAuthor().getId() == userService.getCurrentUser().getId()))
				return "redirect:/recipe/" + recipeId;

			recipeDB.setTitle(recipe.getTitle());
			recipeDB.setShortDescription(recipe.getShortDescription());
			recipeDB.setDifficulty(recipe.getDifficulty());
			recipeDB.setCategory(recipe.getCategory());
			recipeDB.setPrepTime(recipe.getPrepTime());
			recipeDB.setUrlImage(recipe.getUrlImage());
			recipeDB.setInstructions(recipe.getInstructions());

			recipeDB.setCreationDate(LocalDate.now(ZoneId.of("GMT")));

			recipeService.save(recipeDB);

			return "redirect:/editRecipeIngredients/" + recipeId;
		}

		return "redirect:/";
	}

	@GetMapping("/editRecipeIngredients/{id}")
	public String editRecipeIngredients(@PathVariable("id") Long id, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		model.addAttribute("isLogged", userService.isLogged());
		model.addAttribute("isAdmin", userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);

		Recipe recipe = recipeService.findRecipeById(id);

		if (recipe != null) {
			if (!(recipe.getAuthor().getId() == userService.getCurrentUser().getId()))
				return "redirect:/recipe/" + id;
			model.addAttribute(recipe);
			model.addAttribute("ingIndex", recipeService.getLastEmptyIngredientIndex(recipe));
			return "editRecipeIngredients";
		}
		return "redirect:/";
	}

	@GetMapping("/removeRecipeIngredient/{id}/{index}")
	@Transactional
	public String removeRecipeIngredient(@PathVariable("id") Long id, @PathVariable("index") int index, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		model.addAttribute("isLogged", userService.isLogged());
		model.addAttribute("isAdmin", userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);

		Recipe recipe = recipeService.findRecipeById(id);

		if (recipe != null) {
			if (!(recipe.getAuthor().getId() == userService.getCurrentUser().getId()))
				return "redirect:/recipe/" + id;

			recipe.getIngredients().remove(index);
			recipeService.save(recipe);

			model.addAttribute(recipe);
			model.addAttribute("ingIndex", recipeService.getLastEmptyIngredientIndex(recipe));
			return "redirect:/editRecipeIngredients/" + id;
		}
		return "redirect:/";
	}

	@PostMapping("/confirmNewIngredientEdit/{id}")
	@Transactional
	public String confirmNewIngredientEdit(@PathVariable("id") Long id, @RequestParam("ingIndex") int index,
			@ModelAttribute("recipe") Recipe recipe, Model model) {

		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		Recipe recipeDB = recipeService.findRecipeById(id);

		if (recipeDB != null) {
			if (!(recipeDB.getAuthor().getId() == userService.getCurrentUser().getId()))
				return "redirect:/recipe/" + id;

			Ingredient nuovoIng = recipe.getIngredients().get(index);
			if (nuovoIng.getName() != null && !nuovoIng.getName().isBlank()) {
				recipeDB.getIngredients().add(nuovoIng);

				// rimuovi tutti gli ingredienti vuoti prima di salvare
				recipeDB = recipeService.removeEmptyIngredients(recipeDB);

				recipeService.save(recipeDB); // Salva l'aggiunta
				return "redirect:/editRecipeIngredients/" + id;
			}
		}

		return "redirect:/";
	}

	@GetMapping("/confirmRecipeEditUltimated/{id}")
	@Transactional
	public String confirmRecipeEditUltimated(@PathVariable("id") Long id) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}

		Recipe recipe = recipeService.findRecipeById(id);
		if (recipe != null) {
			if (!(recipe.getAuthor().getId() == userService.getCurrentUser().getId()))
				return "redirect:/recipe/" + id;

			recipeService.save(recipe);
			return "redirect:/recipe/" + id;
		}
		return "redirect:/";
	}

	/*
	 * ELIMINAZIONE RICETTA
	 */

	@GetMapping("/deleteRecipe/{id}")
	public String deleteRecipe(@PathVariable("id") Long id, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		Recipe recipe = recipeService.findRecipeById(id);
		if (!(recipe.getAuthor().getId() == userService.getCurrentUser().getId() || userService.isAdmin()))
			return "redirect:/";
		recipeService.delete(recipe);
		return "redirect:/";
	}

}
