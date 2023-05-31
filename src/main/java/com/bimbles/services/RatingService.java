package com.bimbles.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bimbles.entities.Item;
import com.bimbles.entities.NormalUser;
import com.bimbles.entities.Rating;
import com.bimbles.entities.UserItemId;
import com.bimbles.exception.RatingAlreadyExistException;
import com.bimbles.repositories.RatingRepository;
import com.bimbles.utils.types.RatingValue;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RatingService {

	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	NormalUserService normalUserService;
	
	@Autowired
	ItemService itemService;
	
	public RatingValue getRatingValue(Long itemId, Long userId) {
		Optional<Rating> rating = findRating(itemId, userId);
		
		if (rating.isEmpty()) {
			return RatingValue.NO_VALUE;
		} else return rating.get().getValue();
	}
	
	
	public void addRating(Long itemId, Long userId, RatingValue value) {
		NormalUser user = normalUserService.findById(userId);
		Item item = itemService.findById(itemId);

		Optional<Rating> rating = findRating(itemId, userId);
		
		if (rating.isEmpty()) {
			Rating newRating = new Rating(user, item, value);	
			ratingRepository.save(newRating);
			
			item.setNTotalRating(item.getNTotalRating() + 1);
			
			updateAverageRating(item, value);
		} else {
			throw new RatingAlreadyExistException();
		}
	}
	
	public void updateRating(Long itemId, Long userId, RatingValue value) {
		Item item = itemService.findById(itemId);
		
		Optional<Rating> rating = findRating(itemId, userId);
		
		if (rating.isPresent()) {
			Rating updateRating = rating.get();
			updateRating.setValue(value);
			ratingRepository.save(updateRating);
			
			updateAverageRating(item, value);

		} else {
			throw new EntityNotFoundException();
		}
	}
	
	private Optional<Rating> findRating(Long itemId, Long userId) {
		UserItemId compositeId = new UserItemId(userId, itemId);
		
		return ratingRepository.findById(compositeId);
	}
	
	private void updateAverageRating(Item item, RatingValue value) {
		int nTotalRating = item.getNTotalRating();
		
		if (nTotalRating == 1) {
			item.setAverageRating((float) value.ordinal());
		} else {
			List<Rating> ratingList = ratingRepository.findByItem(item);
			float sum = (float) 0;
			for(Rating r : ratingList) {
				sum = ( sum + r.getValue().ordinal() )  ;
			}
			item.setAverageRating( sum/nTotalRating );
		}
		itemService.saveItem(item);
	}
}
