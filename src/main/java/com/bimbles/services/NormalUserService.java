package com.bimbles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bimbles.entities.NormalUser;
import com.bimbles.repositories.NormalUserRepository;

@Service
public class NormalUserService {
	
	@Autowired
	NormalUserRepository userRepository;

	public NormalUser findById(Long normalUserId) {

		return userRepository.getReferenceById(normalUserId);
	}
	
	public NormalUser updateUser(Long userId, NormalUser userBody) {
		NormalUser user = findById(userId);
		user.setUserName(userBody.getUserName()); 
		user.setName(userBody.getName());
		user.setSurname(userBody.getSurname());
		user.setPassword(userBody.getPassword());
		user.setAddress(userBody.getAddress());


		return userRepository.save(user);
	}
}
