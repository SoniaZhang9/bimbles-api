package com.bimbles.controllers;

import com.bimbles.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bimbles.security.doLoggedInUserHaveAccess;
import com.bimbles.services.FavouritesListService;
import java.util.Set;

@RestController
public class FavouritesListController {

	@Autowired
	private FavouritesListService favouritesListService;

	@GetMapping(value="/normal-user/{normalUserId}/favourites")
	public ResponseEntity<Set<Item>> getUserFavourites(@PathVariable("normalUserId") Long normalUserId) {
		doLoggedInUserHaveAccess.check(normalUserId);

		Set<Item> favsItems = favouritesListService.getUserFavourites(normalUserId);
		return new ResponseEntity<>(favsItems, HttpStatus.OK);
	}

	@GetMapping(value="/normal-user/{normalUserId}/favourites/{itemId}")
	public ResponseEntity<Boolean> isItemFavourite(@PathVariable("normalUserId") Long normalUserId,@PathVariable("itemId") Long itemId) {
		doLoggedInUserHaveAccess.check(normalUserId);

		Boolean isItemFavourite = favouritesListService.isItemFavourite(normalUserId, itemId);
		return new ResponseEntity<>(isItemFavourite, HttpStatus.OK);
	}
	
	@PutMapping(value="/normal-user/{normalUserId}/favourites")
	public ResponseEntity<Void> addUserFavourite(@PathVariable("normalUserId") Long normalUserId, @RequestBody Long itemId) {
		doLoggedInUserHaveAccess.check(normalUserId);
		
		favouritesListService.addUserFavourite(normalUserId, itemId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value="/normal-user/{normalUserId}/favourites/{itemId}")
	public ResponseEntity<Void> deleteUserFavourite(@PathVariable("normalUserId") Long normalUserId, @PathVariable("itemId") Long itemId) {
		doLoggedInUserHaveAccess.check(normalUserId);
		
		favouritesListService.deleteUserFavourite(normalUserId, itemId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
