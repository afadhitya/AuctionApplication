package com.fadhit.webcontroller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fadhit.model.Auction;
import com.fadhit.model.AuctionType;
import com.fadhit.model.Item;
import com.fadhit.model.User;
import com.fadhit.service.AuctionService;
import com.fadhit.service.AuctionTypeService;
import com.fadhit.service.ItemService;
import com.fadhit.service.UserService;

@Controller
@RequestMapping("/item")
public class ItemWebController {
	
	@Autowired
	private AuctionTypeService auctionTypeService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuctionService auctionService;
	

	@RequestMapping("/add-form")
	public String addNewForm(Model model){
		List<AuctionType> types = auctionTypeService.getAllAuctionType();
		Item item = new Item();
		
		//get user login data
		User user = userService.getThisUserLogin();
		
		model.addAttribute("user", user);
		model.addAttribute("types", types);
		model.addAttribute("item", item);
		return "add-new-item";
	}
	
	@RequestMapping("/items-list")
	public String itemsList(Model model) {
		List<Item> items = itemService.getAllItem();
		
		model.addAttribute("items", items);
		return "list-item";
	}
	
	@RequestMapping("/detail/{id}")
	public String itemDetail(@PathVariable("id")Integer id, Model model) {
		Item item = itemService.getById(id);
		
		//if time limit has been reach
		if(item.getTimeLimit().before(new Date())) {
			item.setStatus("0");
			itemService.saveItem(item);
		}
		
		Auction auction = new Auction();
		
		//get user data
		User user = userService.getThisUserLogin();
		
		//get highest bid value
		double highestBid = itemService.getHighestValue(item);
		
		//get final price if has been inactive
		String usernameWinner=null;
		if(item.getFinalPrice() > 0) {
			Auction matchAuction = item.getAuctions().stream().filter(a -> a.getBidValue().equals(item.getFinalPrice())).findFirst().get();
			usernameWinner = matchAuction.getUser().getUsername();
		}
		
		model.addAttribute("usernameWinner",usernameWinner);
		model.addAttribute("auction", auction);
		model.addAttribute("item", item);
		model.addAttribute("user", user);
		model.addAttribute("highestBid",highestBid);
		return "detail-item";
	}
}
