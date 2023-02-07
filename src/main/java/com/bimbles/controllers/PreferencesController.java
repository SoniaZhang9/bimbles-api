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
import com.bimbles.entities.Preferences;
import com.bimbles.security.doLoggedInUserHaveAccess;
import com.bimbles.services.NormalUserService;
import com.bimbles.services.PreferencesService;
import com.bimbles.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class PreferencesController {

	@Autowired
	private PreferencesService preferencesService;
	
	@Autowired
	private NormalUserService userService;
	
	@GetMapping(value="/preferences")
	public ResponseEntity<Preferences> getAllPreferencesTypes() {
		Preferences allPreferences = preferencesService.getAllPreferencesTypes();
		return new ResponseEntity<Preferences>(allPreferences, HttpStatus.OK);
	}
	
	@JsonView(Views.NormalUserPreferences.class)
	@PutMapping(value="normal-user/{normalUserId}/preferences")
	public ResponseEntity<NormalUser> saveUserPreferences(@PathVariable("normalUserId") Long normalUserId, @RequestBody Preferences preferences){
		doLoggedInUserHaveAccess.check(normalUserId);
		
		NormalUser savedUserPreferences = preferencesService.saveUserPreferences(normalUserId, preferences);
		return new ResponseEntity<NormalUser>(savedUserPreferences, HttpStatus.OK);
	}
	
	@JsonView(Views.NormalUserPreferences.class)
	@GetMapping(value="/normal-user/{normalUserId}/preferences")
	public ResponseEntity<NormalUser> getUserPreferences(@PathVariable("normalUserId") Long normalUserId) {
		doLoggedInUserHaveAccess.check(normalUserId);
		
		NormalUser normalUser = userService.findById(normalUserId);
		return new ResponseEntity<NormalUser>(normalUser, HttpStatus.OK);
	}
}
