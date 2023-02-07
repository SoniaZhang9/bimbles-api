package com.bimbles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bimbles.entities.NormalUser;
import com.bimbles.security.doLoggedInUserHaveAccess;
import com.bimbles.services.FavouritesListService;
import com.bimbles.services.NormalUserService;
import com.bimbles.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class FavouritesListController {
	@Autowired
	private NormalUserService userService;
	
	@Autowired
	private FavouritesListService favouritesListService;

	@JsonView(Views.NormalUserFavourites.class)
	@GetMapping(value="/normal-user/{normalUserId}/favourites")
	public ResponseEntity<NormalUser> getUserFavourites(@PathVariable("normalUserId") Long normalUserId) {
		doLoggedInUserHaveAccess.check(normalUserId);
		
		NormalUser normalUser = userService.findById(normalUserId);
		return new ResponseEntity<NormalUser>(normalUser, HttpStatus.OK);
	}
	
	@PutMapping(value="/normal-user/{normalUserId}/favourites")
	public ResponseEntity<Void> addUserFavourite(@PathVariable("normalUserId") Long normalUserId, @RequestBody Long itemId) {
		doLoggedInUserHaveAccess.check(normalUserId);
		
		favouritesListService.addUserFavourite(normalUserId, itemId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping(value="/normal-user/{normalUserId}/favourites/{itemId}")
	public ResponseEntity<Void> deleteUserFavourite(@PathVariable("normalUserId") Long normalUserId, @PathVariable("itemId") Long itemId) {
		doLoggedInUserHaveAccess.check(normalUserId);
		
		favouritesListService.deleteUserFavourite(normalUserId, itemId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
