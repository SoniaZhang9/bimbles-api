package com.bimbles.services;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bimbles.entities.NormalUser;
import com.bimbles.entities.Preferences;
import com.bimbles.repositories.UserRepository;
import com.bimbles.utils.types.AllergyType;
import com.bimbles.utils.types.DietType;
import com.bimbles.utils.types.SpecialNeedType;

@Service
public class PreferencesService {
	@Autowired
	private NormalUserService normalUserService;
	
	@Autowired
	private UserRepository normalUserRepository;

	
	public Preferences getAllPreferencesTypes() {
	    SortedSet<DietType> diets = new TreeSet<>(Arrays.asList(DietType.values()));
	    SortedSet<AllergyType> allergies = new TreeSet<>(Arrays.asList(AllergyType.values()));
	    SortedSet<SpecialNeedType> specialNeeds = new TreeSet<>(Arrays.asList(SpecialNeedType.values()));

		return new Preferences(diets, allergies, specialNeeds);
	}
	
	public NormalUser saveUserPreferences(Long normalUserId, Preferences preferences) {
		NormalUser normalUser = normalUserService.findById(normalUserId);
		normalUser.setPreferences(preferences);
		return normalUserRepository.save(normalUser);
	}
}
