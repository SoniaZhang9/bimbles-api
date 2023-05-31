package com.bimbles.controllers;

import java.util.List;

import com.bimbles.security.doLoggedInUserHaveAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.bimbles.entities.Item;
import com.bimbles.services.ItemService;

@RestController
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@GetMapping(value="/item")
	public ResponseEntity<List<? extends Item>> getAllItems(){

		List<Item> items = itemService.findAll();

		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping(value="/item-review")
	public ResponseEntity<List<? extends Item>> getReviewingItems(){

		List<Item> items = itemService.findReviewingItems();

		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping(value="/item-recommendations/{normalUserId}")
	public ResponseEntity<List<? extends Item>> getRecommendations(@PathVariable("normalUserId") Long normalUserId, @RequestParam(value = "refined", required = false, defaultValue = "false") Boolean refined){

		doLoggedInUserHaveAccess.check(normalUserId);
		List<Item> items = itemService.findRecommendations(normalUserId, refined);
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@GetMapping(value="/item/{id}")
	public ResponseEntity<Item> findById(@PathVariable("id") Long id){
		
		Item item = itemService.findById(id);
		
		return new ResponseEntity<>(item, HttpStatus.OK) ;
	}

	@PutMapping (value="/item/{id}/state")
	public ResponseEntity<Void> changeStateToActive(@PathVariable("id") Long id){

		itemService.changeItemStateToActive(id);

		return new ResponseEntity<>(HttpStatus.OK) ;
	}

	@DeleteMapping (value="/item/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id){

		itemService.deleteItem(id);

		return new ResponseEntity<>(HttpStatus.OK) ;
	}
	
	@PostMapping(value="/item", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Item create(@RequestPart("item") Item item, @RequestPart("thumbnail") MultipartFile thumbnail, @RequestPart("pictures") MultipartFile[] files) {
		return itemService.saveItem(item, thumbnail, files);
	}
}
 