package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Category;

public interface CategoryRepository extends CrudRepository<Category,Long> {
	public Category save(Category category);
	
	public List<Category> findAll();
	
	public Optional<Category> findById(Long id);

	public Optional<Category> findByName(String name);
	
	public boolean existsByName(String name);
}
