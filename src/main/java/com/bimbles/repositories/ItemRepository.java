package com.bimbles.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bimbles.entities.Item;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long>{

	Page<Item> findAll(Pageable page);

	@Query(value="SELECT * FROM item where id NOT IN (SELECT item_id FROM user_item_review) LIMIT 2", nativeQuery = true)
	List<Item> findItemsWithoutRatings();
}
