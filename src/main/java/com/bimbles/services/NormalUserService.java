package com.bimbles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bimbles.entities.NormalUser;
import com.bimbles.entities.User;
import com.bimbles.repositories.NormalUserRepository;

@Service
public class NormalUserService {
	
	@Autowired
	NormalUserRepository userRepository;

	public NormalUser findById(Long normalUserId) {
		NormalUser user = userRepository.getReferenceById(normalUserId); 
		
		return user;
	}
	
	public NormalUser updateUser(Long userId, User userBody) {
		NormalUser user = findById(userId);
		user.setUserName(userBody.getUserName()); 
		user.setName(userBody.getName());
		user.setSurname(userBody.getSurname());
		user.setPassword(userBody.getPassword());
		
		NormalUser updatedUser = userRepository.save(user);
		return updatedUser;
	}
}
