package com.fadhit.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable {
	@Id
	private String username;
	private String password;
	private String role;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Auction> auctions = new HashSet<>();

	public Set<Auction> getAuctions() {
		return auctions;
	}
	public void setAuctions(Set<Auction> auctions) {
		this.auctions = auctions;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public User() {
		super();
	}
	
	public User(String username2) {
		this.username = username2;
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
