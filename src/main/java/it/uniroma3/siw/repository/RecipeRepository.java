package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe,Long> {
	
	 @Query("SELECT r FROM Recipe r WHERE r.isActive = true " +
	           "AND (:title IS NULL OR LOWER(r.title) LIKE :title) " +
	           "AND (:categoryId IS NULL OR r.category.id = :categoryId) " +
	           "AND (:difficulty IS NULL OR r.difficulty = :difficulty)"+
		       "ORDER BY r.title")
	 List<Recipe> findByFilters (@Param("title") String title, @Param("categoryId") Long categoryId, @Param("difficulty") String difficulty);

	 List<Recipe> findTop4ByIsActiveTrueOrderByCreationDateDesc();
	 
	 Optional<Recipe> findById(Long id);
	 
	 Iterable<Recipe> findAll();
	
}
