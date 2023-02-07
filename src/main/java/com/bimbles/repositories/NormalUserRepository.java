package com.bimbles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bimbles.entities.NormalUser;

public interface NormalUserRepository extends JpaRepository <NormalUser, Long> {

}
