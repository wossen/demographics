package ie.quest.demographics.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	
	// Let's inject via application.properties
    @Value("${welcome.message}")
    private String message;

	
	@GetMapping
	public String home(Model model) {
		model.addAttribute("message", message);
		return "home";
	}
	
	@GetMapping("/")
	public String home2(Model model) {
		model.addAttribute("message", message);
		return "home";
	}

}