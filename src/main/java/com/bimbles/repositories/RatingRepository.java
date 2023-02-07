package com.bimbles.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bimbles.entities.Item;
import com.bimbles.entities.Rating;
import com.bimbles.entities.UserItemId;

public interface RatingRepository extends JpaRepository<Rating, UserItemId> {
	public List<Rating> findByItem (Item item);
}
