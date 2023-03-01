package com.bimbles.services;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import com.bimbles.entities.Preferences;
import com.bimbles.entities.Rating;
import com.bimbles.repositories.NormalUserRepository;
import com.bimbles.repositories.RatingRepository;

import com.bimbles.utils.types.AllergyType;
import com.bimbles.utils.types.DietType;
import com.bimbles.utils.types.SpecialNeedType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bimbles.entities.Image;
import com.bimbles.entities.Item;
import com.bimbles.repositories.ItemRepository;

@Service
public class ItemService {
	@Autowired
	private NormalUserRepository normalUserRepository;
	@Autowired
	private NormalUserService normalUserService;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private RatingRepository ratingRepository;
	private final Integer maxSimilarUsers = 10;
	private final int maxCandidatesNumber = 6;

	public List<Item> findAll(Pageable page) {
		List<Item> items = itemRepository.findAll(page).toList();
		return items;
	}

	public List<Item> findRecommendations(Pageable page, Long normalUserId) {
		// Get current user preferences
		Preferences preferences = normalUserService.findById(normalUserId).getPreferences();

		SortedSet<Long> allSimilarUsersIds = findSimilarUsersForEachPreference(preferences);
		List<Rating> theirRatings = new ArrayList<>();
		List<Item> recommendations = new ArrayList<>();

		//Dictionary or Map made by Key-value pairs: (Item-> [rating values])
		//All items are unique
		SortedMap<Item,List<Float>> candidates = new TreeMap<>();
		SortedMap<Item,Float> candidatesAverageRating = new TreeMap<>();

		//Remove current user id from similarUsersIds
		allSimilarUsersIds.remove(normalUserId);
		System.out.println("\n- Similar users to user id " + normalUserId + ": " + allSimilarUsersIds);

		//Find similar users ratings
		for (Long id : allSimilarUsersIds){
			theirRatings.addAll(ratingRepository.findItemsRatedByUser(id));
		}
		System.out.println("\n- Ratings made by the similar users: " + theirRatings);

		//Candidates for recommendations
		for(Rating rating : theirRatings){
			candidates.putIfAbsent(rating.getItem(), new ArrayList<>());
			candidates.get(rating.getItem()).add(rating.getOrdinalRatingValue().floatValue());
		}
		System.out.println("\n- Ratings values for each item: " + candidates);

		//Calculate average rating for each candidate
		calculateCandidatesAverageRating(candidates, candidatesAverageRating);

		//Top candidates to get recommended
		List<Map.Entry<Item,Float>> topCandidates = new ArrayList<>(candidatesAverageRating.entrySet());
		topCandidates.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
		System.out.println("\n - Items ordered by descending average rating: \n"
				+ topCandidates.toString().
				replace("\n Item ", " Item ")
				.replace(",",", \n"));

		filterCandidates(topCandidates, recommendations, maxCandidatesNumber);

		/* COLD START PROBLEM SOLUTION: New items + Rest of items:

		   1-. Get new items without ratings
		   2-. Get rest of items

		   Add those items to the end of the recommendations list,
		   so they get a chance to get evaluated by users */

		List<Item> newItems = itemRepository.findItemsWithoutRatings();
		recommendations.addAll(newItems);
		//Rest of items
		List<Item> restItems = itemRepository.findAll();
		//Substract
		restItems.removeAll(recommendations);
		recommendations.addAll(restItems);


		System.out.println("\n Recommendations: " + recommendations);
		return recommendations;
	}


	public Item findById(Long itemId) {
		Item item = itemRepository.getReferenceById(itemId);
		return item;
	}

	public Item saveItem(Item item) {
		Item newItem = itemRepository.save(item);
		return newItem;
	}
	
	public Item saveItem(Item item, MultipartFile thumbnail, MultipartFile[] files){
		if (files != null) {
			try {
				Set<Image> images = createCollectionImages(files);
				Image thumbnailSave = createImage(thumbnail);
				
				item.setThumbnail(thumbnailSave);
				item.setPictures(images);
				
				Item newItem = itemRepository.save(item);
				return newItem;
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return null;
			}
		} else {
			return saveItem(item);
		}
	}
	
	
	private Image createImage(MultipartFile file) throws IOException {
		Image image = new Image(
				file.getOriginalFilename(),
				file.getContentType(),
				file.getBytes()    
				);
		return image;
	}
	
	private Set<Image> createCollectionImages(MultipartFile[] multipartFiles) throws IOException {
		
		Set<Image> images = new HashSet<>();
		
		for (MultipartFile file : multipartFiles) {
			Image image = createImage(file);
			images.add(image);
		}
		
		return images;
	}

	private SortedSet<Long> findSimilarUsersForEachPreference (Preferences preferences){
		SortedSet<Long> allSimilarUsers = new TreeSet<>();

		Iterator<AllergyType> allergies = preferences.getAllergies().iterator();
		Iterator<DietType> diets = preferences.getDiets().iterator();
		Iterator<SpecialNeedType> specialNeeds = preferences.getSpecialNeeds().iterator();

		while(allergies.hasNext()){
			Integer allergyType = allergies.next().ordinal();
			List<Long> userIds = normalUserRepository.findSimilarUsersByAllergyType(allergyType, maxSimilarUsers);
			allSimilarUsers.addAll(userIds);
		}

		while(diets.hasNext()){
			Integer dietType = diets.next().ordinal();
			List<Long> userIds = normalUserRepository.findSimilarUsersByDietType(dietType, maxSimilarUsers);
			allSimilarUsers.addAll(userIds);
		}

		while(specialNeeds.hasNext()){
			Integer specialNeedType = specialNeeds.next().ordinal();
			List<Long> userIds = normalUserRepository.findSimilarUsersBySpecialNeedType(specialNeedType, maxSimilarUsers);
			allSimilarUsers.addAll(userIds);
		}

		return allSimilarUsers;
	}

	private void calculateCandidatesAverageRating(SortedMap<Item,List<Float>> candidates, SortedMap<Item,Float> candidatesAverageRating){
		for(Map.Entry<Item, List<Float>> entry : candidates.entrySet()){
			float totalRatings = entry.getValue().size();
			float average=0;
			for (Float i : entry.getValue()){
				average += i;
			}
			average = average/totalRatings;
			candidatesAverageRating.put(entry.getKey(), average);
		}
	}

	private void filterCandidates(List<Map.Entry<Item,Float>> topCandidates, List<Item> recommendations, Integer maxCandidatesNumber){
		int i = 0;
		for (Map.Entry<Item,Float> entry : topCandidates){
			if(i == maxCandidatesNumber) break;
			else {
				recommendations.add(entry.getKey());
				i++;
			}
		}
	}
}
