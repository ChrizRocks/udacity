package com.example.demo.controllers;

import java.util.Optional;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	public static final Logger log = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			log.error("Error, user '{}' not found.",request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(!item.isPresent()) {
			log.error("Error with your cart, item '{}' not found.",item.get().getName());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.addItem(item.get()));
		log.info("Item '{}' added to your cart.",item.get().getName());
		cartRepository.save(cart);
		return ResponseEntity.ok(cart);
	}
	
	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(!item.isPresent()) {
			log.info("No item '{}' in your cart.", item.get().getName());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		if(cart.getItems().size()==0){
			log.info("No item to remove in cart.");
		}else {
			IntStream.range(0, request.getQuantity())
					.forEach(i -> cart.removeItem(item.get()));
		}
		cartRepository.save(cart);
		log.info("Item successfully removed.");
		return ResponseEntity.ok(cart);
	}

	@GetMapping("/showCart/{username}")
	public ResponseEntity<Cart> showCart(@PathVariable String username){
		log.info("showCart for {}",username);
		User user = userRepository.findByUsername(username);
		if(user==null){
			log.info("No cart found for user {}");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		//cartRepository.save(cart);
		return ResponseEntity.ok(cart);
	}

	@GetMapping("/clearCart/{username}")
	public ResponseEntity<Cart> clearCart(@PathVariable String username){
		log.info("clearCart for {}, used after Ordersubmit.",username);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			log.error("User {} not found.",username);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = new Cart();
		user.setCart(cart);
		userRepository.save(user);
		return ResponseEntity.ok(cart);
	}
		
}
