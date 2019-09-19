package com.fadhit.apicontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.fadhit.model.Auction;
import com.fadhit.model.Item;
import com.fadhit.model.Response;
import com.fadhit.model.User;
import com.fadhit.service.AuctionService;

@RestController
@RequestMapping("/auction")
public class AuctionAPIController {
	
	@Autowired
	private AuctionService auctionService;
	
	//For add a new bid 
	@PostMapping("/bid-value/{id}/{username}")
	public RedirectView saveBid(@ModelAttribute("auction") Auction auction, @PathVariable("id")Integer itemId, @PathVariable("username") String username, RedirectAttributes attribs) {
		auction.setItem(new Item(itemId));
		auction.setUser(new User(username));
		
		RedirectView redirectView = new RedirectView();
		
		Response response = auctionService.saveBid(auction);
		
		if(response.getStatus()) {
			//if success
			redirectView.setUrl("/item/detail/"+auction.getItem().getId()+"?success");
		}else {
			//if not success
			attribs.addFlashAttribute("response", response);
			redirectView.setUrl("/item/detail/"+auction.getItem().getId()+"?error");
		}
		
		return redirectView;
		
	}
	
	//Get all auction data 
	//for production purpose
	@RequestMapping("/get-all")
	public List<Auction> getAllAuction(){
		List<Auction> auctions = auctionService.getAllAuction();
		return auctions;
	}
}
