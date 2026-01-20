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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String formNewRecipe(@ModelAttribute("recipe") Recipe recipe,
			Model model) {
		/* -- INFO NECESSARIE PER SIDEBAR -- */
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
		recipe.setReviews((List<Review>)new ArrayList<Review>());//non so se è corretto?
		//imposta flag temporaneo=true
		recipeService.save(recipe);
		
		redirectAttributes.addFlashAttribute("recipeId", recipe.getId());
		
		return "redirect:/formNewIngredient";
	}
	
	@GetMapping("/formNewIngredient")
	public String formNewIngredient(@ModelAttribute("recipeId") Long recipeId,
			//RedirectAttributes redirectAttributes,
			Model model) {
		/* -- INFO NECESSARIE PER SIDEBAR -- */
		model.addAttribute("isLogged",userService.isLogged());
		model.addAttribute("isAdmin",userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
		
		
		// Se l'ID non è stato passato (es. accesso diretto all'URL), reindirizza alla creazione
        if (recipeId == null) {
        	//String errorMsg = messageSource.getMessage("error.recipe.null_or_invalid_id", null, LocaleContextHolder.getLocale());
        	//model.addAttribute("errorMessage", errorMsg);
        	//attenzione, questo tipo di messaggio non viene mostrato nella pagina formNewRecipe.html, per farlo aggiungi visualizzazione
        	return "redirect:/formNewRecipe";
        }
        
        Optional<Recipe> recipe = recipeService.findById(recipeId);
        //non è stato trovato alcuna ricetta con quell'id
        if(recipe.isEmpty()) {
        	//String errorMsg = messageSource.getMessage("error.recipe.null_or_invalid_id", null, LocaleContextHolder.getLocale());
        	//model.addAttribute("errorMessage", errorMsg);
        	//attenzione, questo tipo di messaggio non viene mostrato nella pagina formNewRecipe.html, per farlo aggiungi visualizzazione
        	return "redirect:/formNewRecipe";	
        }
        
        //qui hai l'oggetto recipe caricato correttamente
        Recipe r = recipe.get();
        model.addAttribute(r);
        model.addAttribute("ingIndex", recipeService.getLastEmptyIngredientIndex(r));
		return "formNewIngredient";
	}
	
	
	@PostMapping("/confirmNewIngredient")
	@Transactional
	public String confirmNewIngredient(@ModelAttribute("recipe") Recipe recipe, // Recupera dal form
			@RequestParam("ingIndex") int index, // Passiamo l'indice
			RedirectAttributes redirectAttributes,
			Model model) {
		
		/*// Se l'ID non è stato passato (es. accesso diretto all'URL), reindirizza alla creazione
        if (recipeId == null) {return "redirect:/formNewRecipe";}
        
        Optional<Recipe> 
        //non è stato trovato alcuna ricetta con quell'id
        if(recipe.isEmpty()) {
        	return "redirect:/formNewRecipe";	
        }*/
		//questo è l'oggetto con tutte le info tranne l'utlimo ingrediente
		Recipe recipeNelDb = recipeService.findById(recipe.getId()).get();
		recipeNelDb.getIngredients().add(recipe.getIngredients().get(index));
        //qui hai l'oggetto recipe caricato correttamente
        //Recipe r = recipe.get();
        //non dovrebbe serivre perché facendo redirect /formNewIng già lì ripete il procedimento
        //model.addAttribute("ingIndex", recipeService.getLastEmptyIngredientIndex(r));
        
		//imposta flag temporaneo=true
		recipeService.save(recipeNelDb);
		
        redirectAttributes.addFlashAttribute("recipeId", recipeNelDb.getId());
				
		return "redirect:/formNewIngredient";
	}
}
