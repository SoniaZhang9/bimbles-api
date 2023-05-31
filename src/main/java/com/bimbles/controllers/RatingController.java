package com.bimbles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bimbles.security.doLoggedInUserHaveAccess;
import com.bimbles.services.RatingService;
import com.bimbles.utils.types.RatingValue;

@RestController
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	@GetMapping(value="/item/{itemId}/rating/{userId}")
	public ResponseEntity<RatingValue> getMyRating(@PathVariable("itemId") Long itemId, @PathVariable("userId") Long userId){
		doLoggedInUserHaveAccess.check(userId);
		
		RatingValue value = ratingService.getRatingValue( itemId, userId );
		
		return new ResponseEntity<>(value, HttpStatus.OK);
	}
	
	@PostMapping(value="/item/{itemId}/rating/{userId}")
	public ResponseEntity<Void> addMyRating(@PathVariable("itemId") Long itemId, @PathVariable("userId") Long userId, @RequestBody RatingValue value){
		doLoggedInUserHaveAccess.check(userId);
		
		ratingService.addRating( itemId, userId, value );

		return new ResponseEntity<>(HttpStatus.OK);
}

	@PutMapping(value="/item/{itemId}/rating/{userId}")
	public ResponseEntity<Void> updateMyRating(@PathVariable("itemId") Long itemId, @PathVariable("userId") Long userId, @RequestBody RatingValue value){
		doLoggedInUserHaveAccess.check(userId);
		
		ratingService.updateRating( itemId, userId, value );
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
