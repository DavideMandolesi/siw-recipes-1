package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Category;
import it.uniroma3.siw.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired CategoryRepository categoryRepository;
	
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	public List<Category> getAllCategories(){
		return (List<Category>)categoryRepository.findAll();
	}
	
	public Category findCategoryById(Long id) {
		return categoryRepository.findById(id).get();
	}

	public void deleteCategoryById(Long id) {
		categoryRepository.deleteById(id);
	}
}
