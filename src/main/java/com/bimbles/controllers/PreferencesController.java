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
		return new ResponseEntity<>(allPreferences, HttpStatus.OK);
	}
	
	@JsonView(Views.NormalUserPreferences.class)
	@PutMapping(value="/preferences/{normalUserId}")
	public ResponseEntity<NormalUser> saveUserPreferences(@PathVariable("normalUserId") Long normalUserId, @RequestBody Preferences preferences){
		NormalUser savedUserPreferences = preferencesService.saveUserPreferences(normalUserId, preferences);
		return new ResponseEntity<>(savedUserPreferences, HttpStatus.OK);
	}
	
	@JsonView(Views.NormalUserPreferences.class)
	@GetMapping(value="/preferences/{normalUserId}")
	public ResponseEntity<NormalUser> getUserPreferences(@PathVariable("normalUserId") Long normalUserId) {
		NormalUser normalUser = userService.findById(normalUserId);
		return new ResponseEntity<>(normalUser, HttpStatus.OK);
	}
}
