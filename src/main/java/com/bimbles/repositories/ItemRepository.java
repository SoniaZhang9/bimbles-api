package com.bimbles.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bimbles.entities.Item;


public interface ItemRepository extends JpaRepository<Item, Long>{

	Page<Item> findAll(Pageable page);
}
