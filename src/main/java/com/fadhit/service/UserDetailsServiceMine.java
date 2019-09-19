package com.fadhit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fadhit.UserPrincipal;
import com.fadhit.model.User;
import com.fadhit.repository.UserRepository;

@Service
public class UserDetailsServiceMine implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User with " +username+ " not found");
		}
		
		return new UserPrincipal(user);
	}

}
