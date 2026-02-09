package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Category;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CategoryService;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class AdminController {
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	RecipeService recipeService;
	@GetMapping("/admin/categoryPanel")
	public String categoryPanel(Model model) {

		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}

		model.addAttribute("defaultCategoryName", Category.DEFAULT_CAT_NAME);

		model.addAttribute("categoryList", categoryService.getAllCategories());
		model.addAttribute("category", new Category());

		return "categoryAdmin";
	}

	@PostMapping("/admin/confirmNewCategory")
	@Transactional
	public String confirmNewCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("defaultCategoryName", Category.DEFAULT_CAT_NAME);
			model.addAttribute("categoryList", categoryService.getAllCategories());
			return "categoryAdmin";
		}
		categoryService.save(category);
		return "redirect:/admin/categoryPanel";
	}

	@GetMapping("/admin/editCategory/{id}")
	public String editCategory(@PathVariable("id") Long id, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		if(categoryService.findCategoryById(id).getName().equals(Category.DEFAULT_CAT_NAME)) {
			return "redirect:/admin/categoryPanel";
		}

		model.addAttribute("category", categoryService.findCategoryById(id));

		return "categoryEdit";
	}

	@PostMapping("/admin/categoryUpdate/{id}")
	@Transactional
	public String updateCategory(@PathVariable("id") Long id, @Valid @ModelAttribute("category") Category category,
			BindingResult bindingResult, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		if(category.getName().equals(Category.DEFAULT_CAT_NAME)) {
			/*errore impossibile modificare la cateogria di default*/
			bindingResult.rejectValue("name", "error.category.editingDefaultCategory",null);
			return "categoryEdit";
		}
		if(bindingResult.hasErrors()) {
			return "categoryEdit";
		}

		// aggiungi controllo errori hasErrors()
		categoryService.save(category);
		return "redirect:/admin/categoryPanel";
	}

	@GetMapping("/admin/deleteCategory/{id}")
	@Transactional
	public String deleteCategory(@PathVariable("id") Long id) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		
		categoryService.deleteCategory(id);
		
		return "redirect:/admin/categoryPanel";
	}

	@GetMapping("/admin/userPanel")
	public String userPanel(@RequestParam(required = false) String name, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}

		model.addAttribute("userList", userService.searchUsers(name));

		return "usersAdmin";
	}

	@GetMapping("/admin/banUser/{id}")
	@Transactional
	public String banUser(@PathVariable("id") Long id, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		User user = userService.getUserById(id);
		if (user.getCredentials().getRole().equals(Credentials.ADMIN_ROLE))
			return "redirect:/admin/userPanel";
		userService.banUser(user);
		userService.save(user);
		return "redirect:/admin/userPanel";
	}

	@GetMapping("/admin/unbanUser/{id}")
	@Transactional
	public String unbanUser(@PathVariable("id") Long id, Model model) {
		// currentUser!= null perché auth permette solo gli autenticati
		if (userService.getCurrentUser().getIsBanned()) {
			return "redirect:/";
		}
		User user = userService.getUserById(id);
		if (user.getCredentials().getRole().equals(Credentials.ADMIN_ROLE))
			return "redirect:/admin/userPanel";
		userService.unbanUser(user);
		userService.save(user);
		return "redirect:/admin/userPanel";
	}
}
