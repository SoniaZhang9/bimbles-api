package com.bimbles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bimbles.entities.NormalUser;
import com.bimbles.security.LoginForm;
import com.bimbles.services.AuthenticationService;
import com.bimbles.utils.JwtUtil;
import com.bimbles.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	JwtUtil jwtUtil;
	
	@PostMapping(value="/login")
	public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

		UserDetails userDetails = authService.loadUserByUsername(loginForm.getUsername());
		
		String jwt = jwtUtil.generateToken(userDetails);
		
		return new ResponseEntity<String>(jwt, HttpStatus.OK);
	} 
	
	@PostMapping(value="/register")
	@JsonView(Views.NormalUserData.class)
	public ResponseEntity<NormalUser> create(@RequestBody NormalUser normalUser){
		NormalUser newNormalUser = authService.createNewUser(normalUser);
		return new ResponseEntity<NormalUser>(newNormalUser, HttpStatus.OK);
	}
}
