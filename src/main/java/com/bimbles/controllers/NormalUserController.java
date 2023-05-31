package com.bimbles.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bimbles.entities.NormalUser;
import com.bimbles.security.doLoggedInUserHaveAccess;
import com.bimbles.services.NormalUserService;
import com.bimbles.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class NormalUserController {
	
	@Autowired
	NormalUserService userService;
	
	@GetMapping(value="/normal-user/{normalUserId}")
	@JsonView(Views.NormalUserData.class)
	public ResponseEntity<NormalUser> findById(@PathVariable("normalUserId") Long userId) {
		doLoggedInUserHaveAccess.check(userId);
		
		NormalUser user = userService.findById(userId);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PutMapping(value="/normal-user/{normalUserId}")
	@JsonView(Views.NormalUserData.class)
	public ResponseEntity<NormalUser> update(@PathVariable("normalUserId") Long userId, @RequestBody NormalUser userBody){
		doLoggedInUserHaveAccess.check(userId);
		NormalUser updatedUser = userService.updateUser(userId, userBody);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
}
