package com.fadhit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fadhit.model.Auction;
import com.fadhit.model.Item;
import com.fadhit.model.Response;
import com.fadhit.model.User;
import com.fadhit.repository.AuctionRepository;

@Service
public class AuctionService {
	
	@Autowired
	private AuctionRepository auctionRepository;
	
	@Autowired 
	private ItemService itemService;
	
	@Autowired 
	private UserService userService;
	
	private static final Integer UNIQUE_LOWEST_BID = 1;
	private static final Integer HIGHEST_BID = 2;
	
	public Response saveBid(Auction auction) {
		//Get item and user to append to auction object
		Item item = itemService.getById(auction.getItem().getId());
		User user = userService.getUserByUsername(auction.getUser().getUsername());
		auction.setItem(item);
		auction.setUser(user);
		
		if(bidMoreThanInitialPrice(auction)) {
			
			if(item.getTimeLimit().before(new Date())){
				//if time limit has been reach
				//set status to inactive
				item.setStatus("0");
				itemService.saveItem(item);
			}
			
			if(itemService.itemIsInactive(item)) {
				//if item has inactive status
				return new Response(false,"Item Is Inactive");
			}
			
			
			if(auction.getItem().getAuctionType().getId() == HIGHEST_BID && bidMoreThanHighestValue(auction) ) {
				//if auction type is HIGHEST BID
				auctionRepository.save(auction);
				return new Response(true);
			}
			else if(auction.getItem().getAuctionType().getId() == HIGHEST_BID && !bidMoreThanHighestValue(auction) ) {
				//if auction type is HIGHEST BID but less than higher value
				return new Response(false,"Bid Must Be Higher than Highest Value");
			}
			else if(auction.getItem().getAuctionType().getId().equals(UNIQUE_LOWEST_BID) && firstBid(auction)) {
				//if auction type is UNIQUE LOWEST BID
				auctionRepository.save(auction);
				return new Response(true);
			}
			else if(auction.getItem().getAuctionType().getId().equals(UNIQUE_LOWEST_BID) && !firstBid(auction)) {
				//if auction type is UNIQUE LOWEST BID and not the first bid
				return new Response(false,"You Has Been Making Bid Before");
			}
		}
		
		return new Response(false,"Bid Must Be Higher than Initial Value");
	}
	
	//fetch all auction
	//---production purpose----
	public List<Auction> getAllAuction(){
		List<Auction> auctions = new ArrayList<>();		
		auctionRepository.findAll().forEach(auctions::add);
		
		return auctions;
	}
	
	//to check is bid more than initial price
	private boolean bidMoreThanInitialPrice(Auction auction) {
		if(auction.getBidValue() > auction.getItem().getInitialPrice()) {
			return true;
		}
		
		return false;
	}
	
	//to check is bid more than highest value (only for highest value type)
	private boolean bidMoreThanHighestValue(Auction auction) {
		if(auction.getBidValue() > itemService.getHighestValue(auction.getItem())) {
			return true;
		}
		return false;
	}
	
	//to make sure there is no double spending
	private boolean firstBid(Auction auction) {
		User bidUser = auction.getUser();
		
		//get set of auction of some item
		Set<Auction> auctions = auction.getItem().getAuctions();
		
		Optional<Auction> matchingObj = auctions.stream().filter(a -> a.getUser().equals(bidUser)).findFirst();
		
		if(matchingObj.isPresent()) {
			return false;
		}
		
		return true;
	}
}
