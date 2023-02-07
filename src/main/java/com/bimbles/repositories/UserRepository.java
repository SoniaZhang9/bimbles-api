package com.bimbles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.bimbles.entities.User;

public interface  UserRepository extends JpaRepository <User, Long> {
	public Optional<User> findByUserName(String userName);
}
