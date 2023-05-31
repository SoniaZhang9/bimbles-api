package com.bimbles.repositories;

import com.bimbles.utils.types.State;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bimbles.entities.Item;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long>{

	List<Item> findItemsByState(State state);

	List<Item> findByAddress_ProvinceAndState (String province, State state);

	@Query(value="SELECT * FROM item where province=:province AND state=0 AND id NOT IN (SELECT item_id FROM user_item_review) LIMIT 2", nativeQuery = true)
	List<Item> findItemsWithoutRatings(String province);
}
