package com.bimbles.controllers;

import java.util.List;

import com.bimbles.security.doLoggedInUserHaveAccess;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bimbles.entities.Item;
import com.bimbles.services.ItemService;

@RestController
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@GetMapping(value="/item")
	public ResponseEntity<List<? extends Item>> getAllItems(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize",  required = false, defaultValue = "5") int pageSize ){
		
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Item> items = itemService.findAll(page);

		return new  ResponseEntity<List<? extends Item>>(items, HttpStatus.OK);
	}

	@GetMapping(value="/item-recommendations/{normalUserId}")
	public ResponseEntity<List<? extends Item>> getRecomendations(@PathVariable("normalUserId") Long normalUserId,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize",  required = false, defaultValue = "5") int pageSize ){

		doLoggedInUserHaveAccess.check(normalUserId);
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Item> items = itemService.findRecommendations(page, normalUserId);

		return new  ResponseEntity<List<? extends Item>>(items, HttpStatus.OK);
	}
	
	@GetMapping(value="/item/{id}")
	public ResponseEntity<Item> findById(@PathVariable("id") Long id){
		
		Item item = (Item) Hibernate.unproxy(itemService.findById(id));
		
		return new ResponseEntity<Item>(item, HttpStatus.OK) ;
	}
	
	@PostMapping(value="/item", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Item create(@RequestPart("item") Item item, @RequestPart("thumbnail") MultipartFile thumbnail, @RequestPart("pictures") MultipartFile[] files) {
		return itemService.saveItem(item, thumbnail, files);
	}
}
 