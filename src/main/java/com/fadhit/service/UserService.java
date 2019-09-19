package com.fadhit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fadhit.model.User;
import com.fadhit.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean createUser (User user) {
		if(usernameExist(user.getUsername())) {
			return false;
		}
			
		userRepository.save(user);
		return true;
	}
	
	private boolean usernameExist(String username) {
		User user = userRepository.findByUsername(username);
		if(user != null)
			return true;
		
		return false;
	}
	
	public User getUserByUsername(String username){
		User user = new User();
		user = userRepository.findByUsername(username);
		return user;
	}
	
	// get user login 
	public User getThisUserLogin() {
		User user = new User();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails)
			user = this.getUserByUsername(((UserDetails)principal).getUsername());
		
		return user;
	}

}
