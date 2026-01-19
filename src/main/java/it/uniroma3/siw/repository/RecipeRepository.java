package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe,Long> {
	Optional<Recipe> findById(Long id);
}
