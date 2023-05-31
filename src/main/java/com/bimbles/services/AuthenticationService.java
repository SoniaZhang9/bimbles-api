package com.bimbles.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bimbles.entities.NormalUser;
import com.bimbles.entities.User;
import com.bimbles.exception.UserNameAlreadyExistException;
import com.bimbles.repositories.UserRepository;
import com.bimbles.security.CustomUserDetails;

@Service
public class AuthenticationService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) {

		Optional<User> user = userRepository.findByUserName(userName);
		
		if(user.isEmpty()) {
			throw new UsernameNotFoundException(userName + "no encontrado");
		}
		
		return new CustomUserDetails(user.get());
	}
	

	public NormalUser createNewUser(NormalUser normalUser) {
		
		Optional<User> existUserName = userRepository.findByUserName(normalUser.getUserName());
		
		if(existUserName.isEmpty()) {
			return userRepository.save(normalUser);
		} else {
			throw new UserNameAlreadyExistException(normalUser.getUserName());
		}
	}
}
 