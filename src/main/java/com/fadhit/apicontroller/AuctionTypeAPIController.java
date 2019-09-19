package com.fadhit.apicontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fadhit.model.AuctionType;
import com.fadhit.service.AuctionTypeService;

@RestController
public class AuctionTypeAPIController {
	
	@Autowired
	private AuctionTypeService auctionTypeService;
	
	//for save a new type of auction
	//--production purpose only--
	@PostMapping("/type")
	public RedirectView saveAuctionType(@RequestBody AuctionType auctionType) {
		auctionTypeService.saveAuctionType(auctionType);
		
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl("/types");
				
		return redirectView;
	}
	
	//for fetch all auction types
	@RequestMapping("/types")
	public List<AuctionType> getAllAuctionType() {
		List<AuctionType> types = auctionTypeService.getAllAuctionType();
		return types;
	}
}
