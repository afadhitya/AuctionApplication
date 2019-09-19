package com.fadhit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fadhit.model.AuctionType;
import com.fadhit.repository.AuctionTypeRepository;

@Service
public class AuctionTypeService {
	
	@Autowired
	private AuctionTypeRepository auctionTypeRepository;
	
	public List<AuctionType> getAllAuctionType() {
		List<AuctionType> types = new ArrayList<>();
		auctionTypeRepository.findAll().forEach(types::add);
		
		return types;
	}
	
	public boolean saveAuctionType(AuctionType auctionType) {
		auctionTypeRepository.save(auctionType);
		
		return true;
	}
}
