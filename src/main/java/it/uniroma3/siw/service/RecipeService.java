package it.uniroma3.siw.service;

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

	public Recipe findRecipeById(Long id) {
		return recipeRepository.findById(id).get();
	}
}
