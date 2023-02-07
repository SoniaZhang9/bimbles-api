package com.bimbles.entities;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.SortedSet;

import com.bimbles.utils.Views;
import com.bimbles.utils.types.AllergyType;
import com.bimbles.utils.types.DietType;
import com.bimbles.utils.types.SpecialNeedType;
import com.fasterxml.jackson.annotation.JsonView;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Preferences {	
	@Column(name = "DIET", nullable = true)
	@ElementCollection
	@JsonView(Views.NormalUserPreferences.class)
	private SortedSet<DietType> diets;
	
	@Column(name = "ALLERGY", nullable = true)
	@ElementCollection
	@JsonView(Views.NormalUserPreferences.class)
	private SortedSet<AllergyType> allergies;
	
	@Column(name = "SPECIAL_NEED", nullable = true)
	@ElementCollection
	@JsonView(Views.NormalUserPreferences.class)
	private SortedSet<SpecialNeedType> specialNeeds;
}
