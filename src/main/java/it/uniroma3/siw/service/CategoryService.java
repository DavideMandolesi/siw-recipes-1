package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Category;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	@Autowired CategoryRepository categoryRepository;
	@Autowired RecipeService recipeService;
	
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	public List<Category> getAllCategories(){
		return (List<Category>)categoryRepository.findAll();
	}
	
	public Category findCategoryByName(String name) {
		return categoryRepository.findByName(name).get();
	}
	
	public Category findCategoryById(Long id) {
		return categoryRepository.findById(id).get();
	}
	
	public void deleteCategoryById(Long id) {
		categoryRepository.deleteById(id);
	}

	public boolean existsById(Long id) {
		return categoryRepository.existsById(id);
	}
	
	@Transactional
    public void deleteCategory(Long idToDelete) {
        Category toDelete = categoryRepository.findById(idToDelete).orElse(null);
        
        //Non elimino il default o se è null
        if (toDelete == null || toDelete.getName().equals(Category.DEFAULT_CAT_NAME)) {return;}

        Category defaultCat = categoryRepository.findByName(Category.DEFAULT_CAT_NAME)
                .orElseGet(() -> {
                    Category newDefault = new Category();
                    newDefault.setName(Category.DEFAULT_CAT_NAME);
                    newDefault.setDescription("Categoria predefinita per ricette senza categoria specifica.");
                    return categoryRepository.save(newDefault);
                });

        //ora ho sicuramente la default
        for (Recipe r : toDelete.getRecipes()) {
            r.setCategory(defaultCat);
            recipeService.save(r);
        }

        categoryRepository.delete(toDelete);
    }

}
