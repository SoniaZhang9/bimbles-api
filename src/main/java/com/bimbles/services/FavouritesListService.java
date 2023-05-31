package com.bimbles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bimbles.entities.Item;
import com.bimbles.entities.NormalUser;
import com.bimbles.repositories.UserRepository;

import java.util.Set;

@Service
public class FavouritesListService {

	@Autowired
	private UserRepository normalUserRepository;
	
	@Autowired
	private NormalUserService normalUserService;
	
	@Autowired
	private ItemService itemService;

	public void addUserFavourite(Long normalUserId, Long itemId) {
		
		NormalUser normalUser = normalUserService.findById(normalUserId);
		
		Item item = itemService.findById(itemId);
		
		normalUser.getFavouritesList().getFavsList().add(item);
		
		normalUserRepository.save(normalUser);
	}
	
	public void deleteUserFavourite(Long normalUserId, Long itemId) {
		
		NormalUser normalUser = normalUserService.findById(normalUserId);
		
		Item item = itemService.findById(itemId);
		
		normalUser.getFavouritesList().getFavsList().remove(item);
		
		normalUserRepository.save(normalUser);
	}

	public Set<Item> getUserFavourites(Long normalUserId) {

		NormalUser normalUser = normalUserService.findById(normalUserId);

		return normalUser.getFavouritesList().getFavsList();
	}

	public Boolean isItemFavourite(Long normalUserId, Long itemId) {

		Item item = itemService.findById(itemId);

		return getUserFavourites(normalUserId).contains(item);
	}
}
