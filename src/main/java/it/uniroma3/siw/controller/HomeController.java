package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.UserService;

@Controller
class HomeController {
	@Autowired UserService userService;

	@GetMapping("/")
	public String home(Model model){
		model.addAttribute("isLogged",userService.isLogged());
		model.addAttribute("isAdmin",userService.isAdmin());
		model.addAttribute("currentUser", userService.getCurrentUser());
		return "home";
	}
}