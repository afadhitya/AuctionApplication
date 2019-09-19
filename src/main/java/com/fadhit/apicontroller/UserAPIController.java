package com.fadhit.apicontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fadhit.model.User;
import com.fadhit.service.UserService;

@RestController
public class UserAPIController {
	
	@Autowired
	private UserService userService;
	
	//create a new user from sign up page
	@PostMapping("/user-create")
	public RedirectView createUserForm(@ModelAttribute("user")User user){
		//encrypt password
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
	    String encodedPassword = encoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
	    
	    RedirectView redirectView = new RedirectView();
	    
		if(userService.createUser(user)) {
			//if create user success
		    redirectView.setUrl("/login");
		}else {
			//if username has been taken by somebody else
			redirectView.setUrl("/signup?error");
		}
		
		return redirectView;
	}
	
	//Get User
	//----for production purpose-----
	@RequestMapping("/user/{username}")
	public User getSomeUser(@PathVariable("username")String username) {
		User user = userService.getUserByUsername(username);
		
		return user;
	}
}
