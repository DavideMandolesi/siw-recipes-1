package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.RecipeRepository;

@Service
public class RecipeService {
	@Autowired RecipeRepository recipeRepository;
	
	public Recipe save(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	public Optional<Recipe> findById(Long id) {
		return recipeRepository.findById(id);
	}
	
	public Recipe findRecipeById(Long id) {
		return recipeRepository.findById(id).orElse(null);
	}
	
	public int getLastEmptyIngredientIndex(Recipe r) {
		if(!r.getIngredients().isEmpty()) {
        	//non è vuota, assicurati che l'ultimo elemento sia vuoto
        	if(r.getIngredients().getLast().getName()!=null) {r.getIngredients().add(new Ingredient());}
        	//passa l'indice dell'ultimo elemento (vuoto) alla vista
        	return r.getIngredients().size()-1;
        }
		//se è vuota l'indice in cui inserire è 0
		r.getIngredients().add(new Ingredient());
		return 0;
		
	}
	
	public void setActive(Recipe recipe) {
		recipe.setIsActive(true);
	}

	public Recipe removeEmptyIngredients(Recipe recipe) {
		for (int j = 0; j < recipe.getIngredients().toArray().length; j++) {
			if(recipe.getIngredients().get(j)==null) {
				recipe.getIngredients().remove(j);
			}
			else if(recipe.getIngredients().get(j).getName().isBlank())
				recipe.getIngredients().remove(j);
		}
		return recipe;
	}
	
	public void delete(Recipe recipe) {
		recipeRepository.delete(recipe); 
	}

	public List<Recipe> getLatest4Recipes() {
		return recipeRepository.findTop4ByIsActiveTrueOrderByCreationDateDesc();
	}
	
	public List<Recipe> getAllRecipes() {
		return (List<Recipe>)recipeRepository.findAll();
	}

	public List<Recipe> searchRecipes(String title, Long categoryId, String difficulty) {
		// Se i parametri sono stringhe vuote, li tratto come null	    
		String titleParam = null;
	    if (title != null && !title.isBlank()) {
	        // Prepara la stringa lowercase compatibile alla ricerca parziale: "Pasta" diventa "%pasta%"
	        titleParam = "%" + title.toLowerCase() + "%";
	    }
	    if(title != null && title.isBlank()) {titleParam=null;}
	    if(difficulty != null && difficulty.isBlank()) {difficulty=null;}
	    
		return recipeRepository.findByFilters(titleParam, categoryId, difficulty);
	}

	//aggiungi .plusDays(1) per testare l'eliminazione di ricette reate oggi.
	//così il metodo cancellerebbe  tutte le ricette "create prima di domani"
	public void deleteInactiveRecipes() {
		recipeRepository.deleteByIsActiveFalseAndCreationDateBefore(LocalDate.now());
	}
}
