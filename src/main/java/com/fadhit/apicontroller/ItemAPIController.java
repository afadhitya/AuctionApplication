package com.fadhit.apicontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.fadhit.model.AuctionType;
import com.fadhit.model.Item;
import com.fadhit.model.Response;
import com.fadhit.model.User;
import com.fadhit.service.ItemService;
import com.fadhit.service.UserService;

@RestController
public class ItemAPIController{

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserService userService;
	
	//Fetch all item 
	@RequestMapping("/items")
	public List<Item> getAllItem() {
		List<Item> items = itemService.getAllItem();
		return items;
	}
	
	//for save an new item
	@PostMapping("/item")
	public RedirectView saveItem(@ModelAttribute Item item) {
		RedirectView redirectView = new RedirectView();
		
		//get user login
		User user = userService.getThisUserLogin();
		
		if(!user.getRole().equals("ADMIN")) {
			//if not an ADMIN
			redirectView.setUrl("/item/add-form");
			return redirectView;
		}
		
		//if everything is OK
		itemService.saveItem(item);
		
		redirectView.setUrl("/item/items-list");
		return redirectView;
	}
	
	//change status of item to inactive
	@RequestMapping("/item/inactive/{idItem}")
	public RedirectView makeItemInactive(@PathVariable("idItem") Integer idItem) {
		//Get user to check permission
		User user = userService.getThisUserLogin();
		
		RedirectView redirectView = new RedirectView();
	   		
		if (!(user.getRole().equals("ADMIN"))) {
			//if user not an ADMIN
			redirectView.setUrl("/item/detail/"+idItem+"?warning");
			return redirectView;
		}
		
		//If user an ADMIN
		
		Item item = itemService.getById(idItem);
		
		itemService.makeItemInactive(item);
		
		redirectView.setUrl("/item/detail/"+idItem);
		return redirectView;
	}
	
	//change status of item to active
	@RequestMapping("/item/active/{idItem}")
	public RedirectView makeItemActiveAgain(@PathVariable("idItem") Integer idItem, RedirectAttributes attribs) {
		//Get user to check permission
		User user = userService.getThisUserLogin();
		
		RedirectView redirectView = new RedirectView();
	   		
		//if user not an ADMIN
		if (!(user.getRole().equals("ADMIN"))) {
			redirectView.setUrl("/item/detail/"+idItem+"?warning");
			return redirectView;
		}
		
		//-------If user an ADMIN-------------
		
		Item item = itemService.getById(idItem);
		
		if(item.getTimeLimit().before(new Date())) {
			//If time limit has been reach			
			attribs.addFlashAttribute("response", new Response(false,"The Time Limit Has Been Reach!"));
			redirectView.setUrl("/item/detail/"+idItem+"?error");
			return redirectView;
		}
		
		//if everything is OK
		itemService.makeItemActiveAgain(item);
		
		redirectView.setUrl("/item/detail/"+idItem);
		return redirectView;
	}
}
