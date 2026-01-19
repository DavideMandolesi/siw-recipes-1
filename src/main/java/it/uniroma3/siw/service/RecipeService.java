package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.RecipeRepository;

@Service
public class RecipeService {
	@Autowired RecipeRepository recipeRepository;
	
	public Recipe save(Recipe recipe) {
		return recipeRepository.save(recipe);
	}
}
