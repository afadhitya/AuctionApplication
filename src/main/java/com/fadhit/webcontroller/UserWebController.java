package com.fadhit.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.fadhit.model.User;
import com.fadhit.service.UserService;

@Controller
public class UserWebController {
	
	@Autowired
	private UserService	userService;
	
	@RequestMapping("/")
	public RedirectView home(){
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/item/items-list");
				
		return redirectView;
	}
	
	@RequestMapping("/signup")
	public String signupForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "signup";
	}
	
	@RequestMapping("/login")
	public String loginPage() {return "login";}
	
	@RequestMapping("/profile")
	public String profilePage(Model model) {
		User user = userService.getThisUserLogin();	
		
		model.addAttribute("user",user);
		return "my-profile";
	}
}
