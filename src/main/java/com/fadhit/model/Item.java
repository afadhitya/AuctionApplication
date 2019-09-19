package com.fadhit.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Item implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	private double initialPrice;
	
	@Nullable
	@Column(nullable = true)
	private double finalPrice;
	
	private String status;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timeLimit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auctionTypeId", nullable = false)
	private AuctionType auctionType;
	
	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private Set<Auction> auctions = new HashSet<>();

//	public Item(Integer id, String name, double initialPrice, String status, Date timeLimit, Integer auctionTypeId) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.initialPrice = initialPrice;
//		this.status = status;
//		this.timeLimit = timeLimit;
//		this.auctionType = new AuctionType(auctionTypeId,"","");
//	}

	public Item() {
		super();
	}

	public Item(Integer itemId) {
		this.id = itemId;
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(double initialPrice) {
		this.initialPrice = initialPrice;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Date timeLimit) {
		this.timeLimit = timeLimit;
	}

	@JsonIgnore
	public AuctionType getAuctionType() {
		return auctionType;
	}

	@JsonIgnore
	public void setAuctionType(AuctionType auctionType) {
		this.auctionType = auctionType;
	}

	public Set<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(Set<Auction> auctions) {
		this.auctions = auctions;
	}

	
}
