package com.bimbles.entities;

import java.util.SortedSet;

import com.bimbles.utils.types.*;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends ItemPhysical{

	private CuisineType cuisineType;
	
	@Column(name = "ALLERGY")
	@ElementCollection
	private SortedSet<AllergyType> allergies;
}
