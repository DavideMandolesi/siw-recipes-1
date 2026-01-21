package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Category;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CategoryService;
import it.uniroma3.siw.service.UserService;
import jakarta.transaction.Transactional;

@Controller
public class AdminController {
	@Autowired UserService userService;
	@Autowired CategoryService categoryService;
	
	@GetMapping("/admin/categoryPanel")
	public String categoryPanel(Model model) {
		model.addAttribute("isLogged",userService.isLogged());
		model.addAttribute("isAdmin",userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
		
		model.addAttribute("categoryList", categoryService.getAllCategories());
		model.addAttribute("category", new Category());
		
		return "categoryAdmin";
	}
	
	@PostMapping("/admin/confirmNewCategory")
	@Transactional
	public String confirmNewCategory(@ModelAttribute("Category") Category category) {
		if(category!=null) categoryService.save(category);
		return"redirect:/admin/categoryPanel";
	}
	
	@GetMapping("/admin/editCategory/{id}")
	public String editCategory(@PathVariable("id") Long id,
			Model model) {
		model.addAttribute("isLogged",userService.isLogged());
		model.addAttribute("isAdmin",userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		model.addAttribute("defaultProfileUrlImage", User.DEFAULT_URL_PROFILE_PIC);
		
		model.addAttribute("category", categoryService.findCategoryById(id));
		
		return "categoryEdit";
	}
	
	@PostMapping("/admin/categoryUpdate/{id}")
	@Transactional
	public String updateCategory(@PathVariable("id") Long id,
			@ModelAttribute("category") Category category,
			BindingResult result) {
		//aggiungi controllo errori hasErrors()
		categoryService.save(category);
		return"redirect:/admin/categoryPanel";
	}
	
	@GetMapping("/admin/deleteCategory/{id}")
	public String deleteCategory(@PathVariable("id") Long id) {
		categoryService.deleteCategoryById(id);
		
		return "redirect:/admin/categoryPanel";
	}
}
