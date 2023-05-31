package com.bimbles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bimbles.repositories.UserRepository;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class BimblesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BimblesApiApplication.class, args);
	}

}
