package com.bimbles.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bimbles.entities.Item;
import com.bimbles.entities.Rating;
import com.bimbles.entities.UserItemId;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, UserItemId> {
	List<Rating> findByItem (Item item);

	@Query(value = "SELECT * FROM user_item_review WHERE user_id=:userId", nativeQuery = true)
	List<Rating> findItemsRatedByUser (Long userId);
}
