package com.fadhit.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Auction implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", nullable = false)
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
	private User user;
	
	private double bidValue;
	

	public Auction() {
		super();
	}

	public Auction(Integer id, Integer itemId, String username, double bidValue) {
		super();
		this.id = id;
		this.item = new Item(itemId);
		this.user = new User(username);
		this.bidValue = bidValue;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonIgnore
	public Item getItem() {
		return item;
	}

	@JsonIgnore
	public void setItem(Item item) {
		this.item = item;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	@JsonIgnore
	public void setUser(User user) {
		this.user = user;
	}

	public Double getBidValue() {
		return bidValue;
	}

	public void setBidValue(double bidValue) {
		this.bidValue = bidValue;
	}
	
	
	
	
	
}
