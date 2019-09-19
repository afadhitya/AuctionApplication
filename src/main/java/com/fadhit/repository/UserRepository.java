package com.fadhit.repository;

import org.springframework.data.repository.CrudRepository;

import com.fadhit.model.User;

public interface UserRepository extends CrudRepository <User, String>{
	public User findByUsername(String username);
}
