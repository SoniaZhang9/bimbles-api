package com.bimbles.controllers;

import com.bimbles.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bimbles.entities.NormalUser;
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

	@AllArgsConstructor
	@Getter
	 static class LoginForm {
		private String username;
		private String password;
	}

	@AllArgsConstructor
	@Getter
	static class LoginResponse {
		private String role;
		private String jwt;
		private Long userId;
	}
	
	@PostMapping(value="/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginForm loginForm) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

		CustomUserDetails userDetails = (CustomUserDetails) authService.loadUserByUsername(loginForm.getUsername());
		
		String jwt = jwtUtil.generateToken(userDetails);
		String role = userDetails.getAuthorities().get(0).toString();
		LoginResponse response = new LoginResponse(role, jwt, userDetails.getId());

		return new ResponseEntity<>(response, HttpStatus.OK);
	} 
	
	@PostMapping(value="/register")
	@JsonView(Views.NormalUserData.class)
	public ResponseEntity<NormalUser> create(@RequestBody NormalUser normalUser){
		NormalUser newNormalUser = authService.createNewUser(normalUser);
		return new ResponseEntity<>(newNormalUser, HttpStatus.OK);
	}
}
