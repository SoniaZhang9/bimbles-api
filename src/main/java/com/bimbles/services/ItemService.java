package com.bimbles.services;

import java.io.IOException;
import java.util.*;
import com.bimbles.entities.*;
import com.bimbles.repositories.NormalUserRepository;
import com.bimbles.repositories.RatingRepository;
import com.bimbles.utils.types.AllergyType;
import com.bimbles.utils.types.DietType;
import com.bimbles.utils.types.SpecialNeedType;
import com.bimbles.utils.types.State;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.bimbles.repositories.ItemRepository;

@Service
public class ItemService {
	@Autowired
	private NormalUserRepository normalUserRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private NormalUserService normalUserService;
	private final int maxCandidatesNumber = 6;

	public List<Item> findAll() {
		return itemRepository.findItemsByState(State.ACTIVE);
	}

	public List<Item> findReviewingItems() {
		return itemRepository.findItemsByState(State.REVIEWING);
	}

	public List<Item> findRecommendations(Long normalUserId, Boolean refined) {
		NormalUser loggedUser = normalUserService.findById(normalUserId);
		// Get current user preferences
		Preferences preferences = loggedUser.getPreferences();
		// Get current user province
		String province = loggedUser.getAddress().getProvince();

		//Find similar users
		SortedSet<Long> allSimilarUsersIds = findSimilarUsersForEachPreference(preferences, province, refined);
		List<Rating> theirRatings = new ArrayList<>();
		List<Item> recommendations = new ArrayList<>();

		//Declarations : Maps made by Key-value pairs: (Item-> [rating values])
		//All items are unique
		SortedMap<Item,List<Float>> candidates = new TreeMap<>();
		SortedMap<Item,Float> candidatesAverageRating = new TreeMap<>();

		//Remove current user id from similarUsersIds
		allSimilarUsersIds.remove(normalUserId);
		System.out.println("\n- Similar users to user id " + normalUserId + ": " + allSimilarUsersIds);

		//Find ratings made by similar users
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

		filterCandidates(topCandidates, recommendations);

		/* COLD START PROBLEM SOLUTION: New items + Rest of items:

		   1-. Get new items without ratings
		   2-. Get rest of items

		   Add those items to the end of the recommendations list,
		   so they get a chance to get evaluated by users */

		List<Item> newItems = itemRepository.findItemsWithoutRatings(province);
		recommendations.addAll(newItems);
		//Rest of items
		List<Item> restItems = itemRepository.findByAddress_ProvinceAndState(province, State.ACTIVE);
		//Subtract
		restItems.removeAll(recommendations);
		recommendations.addAll(restItems);

		System.out.println("\n Recommendations: " + recommendations);

		List<Item> recommendationsUnproxied = new ArrayList<>();

		for (Item item : recommendations){
			recommendationsUnproxied.add((Item) Hibernate.unproxy(item));
		}
		return recommendationsUnproxied;
	}


	public Item findById(Long itemId) {
		return (Item) Hibernate.unproxy(itemRepository.getReferenceById(itemId));
	}

	public Item saveItem(Item item) {
		return itemRepository.save(item);
	}

	public void deleteItem(Long itemId) {
		itemRepository.deleteById(itemId);
	}

	public void changeItemStateToActive(Long itemId){
		Item item = findById(itemId);
		item.setState(State.ACTIVE);
		saveItem(item);
	}

	public Item saveItem(Item item, MultipartFile thumbnail, MultipartFile[] files){
		if (files != null) {
			try {
				Set<Image> images = createCollectionImages(files);
				Image thumbnailSave = createImage(thumbnail);

				item.setThumbnail(thumbnailSave);
				item.setPictures(images);

				return itemRepository.save(item);

			} catch (IOException e) {
				System.out.println(e.getMessage());
				return null;
			}
		} else {
			return saveItem(item);
		}
	}


	private Image createImage(MultipartFile file) throws IOException {
		return new Image(
				file.getOriginalFilename(),
				file.getContentType(),
				file.getBytes()
				);
	}

	private Set<Image> createCollectionImages(MultipartFile[] multipartFiles) throws IOException {

		Set<Image> images = new HashSet<>();

		for (MultipartFile file : multipartFiles) {
			Image image = createImage(file);
			images.add(image);
		}

		return images;
	}

	private SortedSet<Long> findSimilarUsersForEachPreference (Preferences preferences, String province, Boolean refined){
		SortedSet<Long> allSimilarUsers = new TreeSet<>();

		Iterator<AllergyType> allergies = preferences.getAllergies().iterator();
		Iterator<DietType> diets = preferences.getDiets().iterator();
		Iterator<SpecialNeedType> specialNeeds = preferences.getSpecialNeeds().iterator();

		Integer maxSimilarUsers = refined ? 99999 : 10;

		while(allergies.hasNext()){
			Integer allergyType = allergies.next().ordinal();
			List<Long> userIds = normalUserRepository.findSimilarUsersByAllergyType(allergyType, maxSimilarUsers, province);
			allSimilarUsers.addAll(userIds);
		}

		while(diets.hasNext()){
			Integer dietType = diets.next().ordinal();
			List<Long> userIds = normalUserRepository.findSimilarUsersByDietType(dietType, maxSimilarUsers,  province);
			allSimilarUsers.addAll(userIds);
		}

		while(specialNeeds.hasNext()){
			Integer specialNeedType = specialNeeds.next().ordinal();
			List<Long> userIds = normalUserRepository.findSimilarUsersBySpecialNeedType(specialNeedType, maxSimilarUsers, province);
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

	private void filterCandidates(List<Map.Entry<Item,Float>> topCandidates, List<Item> recommendations){
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
