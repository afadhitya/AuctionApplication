package com.fadhit.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fadhit.exception.ResourceNotFoundException;
import com.fadhit.model.Auction;
import com.fadhit.model.Item;
import com.fadhit.repository.ItemRepository;

@Service
public class ItemService {
	
	private static final Integer UNIQUE_LOWEST_BID = 1;
	private static final Integer HIGHEST_BID = 2;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> getAllItem(){
		List<Item> items = new ArrayList<Item>();
		
		itemRepository.findAll().forEach(items::add);
		
		return items;
	}
	
	public boolean saveItem(Item item) {
		itemRepository.save(item);
		
		return true;
	}
	

	public double getHighestValue(Item itemParam) {
		Item item = itemParam;
		
		Set<Auction> auctions = item.getAuctions();
		Optional<Auction> highestAuction = auctions.stream()
											.max(Comparator.comparingDouble(Auction::getBidValue));
		if(highestAuction.isPresent()) {
			return highestAuction.get().getBidValue();
		}else {
			return 0;
		}
		
	}
	
	public Item getById(Integer id) {
		Item item =  itemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		
		return item;
	}

	public void makeItemInactive(Item item) {
		double finalPrice;
		
		if(item.getAuctionType().getId() == HIGHEST_BID) {
			finalPrice = getHighestValue(item);
		}else {
			List<Double> bidValueList = makeBidValueList(item);
			finalPrice = getLowestUniqueValue(bidValueList);
		}
		
		item.setStatus("0");
		item.setFinalPrice(finalPrice);
		itemRepository.save(item);
	}
	
	private List<Double> makeBidValueList (Item item){
		Set<Auction> auctions = item.getAuctions();
		List<Double> bidValueList = new ArrayList<>();
		
		if(auctions.size() != 0) {
			for(Auction auction : auctions) {
				bidValueList.add(auction.getBidValue());
			}
		}
		
		
		return bidValueList;
	}
	
	//for get lowest unique value (auction type: LOWEST UNIQUE VALUE)
	private double getLowestUniqueValue(List<Double> bidValueList) {
		
		if(bidValueList.size() == 0) {
			return 0;
		}
		
		Collections.sort(bidValueList);
		Double lowestTemp = bidValueList.get(0);
		
		if(Collections.frequency(bidValueList, lowestTemp) > 1) {
			while(bidValueList.remove(lowestTemp));
			
			getLowestUniqueValue(bidValueList);
		}
		
		return lowestTemp = bidValueList.get(0);
		
	}

	//to set item to active again
	public void makeItemActiveAgain(Item item) {
		item.setStatus("1");
		itemRepository.save(item);
	}	
	
	//check is item inactive
	public boolean itemIsInactive(Item item) {
		return item.getStatus().equals("0");
	}
}
