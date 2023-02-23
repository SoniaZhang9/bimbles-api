package com.bimbles.services;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import com.bimbles.entities.Preferences;
import com.bimbles.entities.Rating;
import com.bimbles.repositories.NormalUserRepository;
import com.bimbles.repositories.RatingRepository;
import com.bimbles.utils.RatingComparator;
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

import static java.lang.Math.round;

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
	private final Integer max = 10;
	private final DecimalFormat df = new DecimalFormat("0.00");

	public List<Item> findAll(Pageable page) {
		List<Item> items = itemRepository.findAll(page).toList();
		return items;
	}

	public List<Item> findRecommendations(Pageable page, Long normalUserId) {
		Preferences preferences = normalUserService.findById(normalUserId).getPreferences();

		SortedSet<Long> allSimilarUsersIds = findSimilarUsersForEachPreference(preferences);
		List<Rating> theirRatings = new ArrayList<>();
		SortedMap<Item,List<Float>> candidates = new TreeMap<>();

		//Remove current user id from set
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

		//Calculate average rating for each item

		for(Map.Entry<Item, List<Float>> entry : candidates.entrySet()){
			float totalRatings = entry.getValue().size();
			float average=0;
			for (Float i : entry.getValue()){
				average += i;
			}
			average = average/totalRatings;
			entry.setValue(List.of(average));
		}

		System.out.println("\n - Average rating by item " + candidates);

		List<Item> items = itemRepository.findAll(page).toList();
		return items;
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
			List<Long> userIds = normalUserRepository.findSimilarUsersByAllergyType(allergyType, max);
			allSimilarUsers.addAll(userIds);
		}

		while(diets.hasNext()){
			Integer dietType = diets.next().ordinal();
			List<Long> userIds = normalUserRepository.findSimilarUsersByDietType(dietType, max);
			allSimilarUsers.addAll(userIds);
		}

		while(specialNeeds.hasNext()){
			Integer specialNeedType = specialNeeds.next().ordinal();
			List<Long> userIds = normalUserRepository.findSimilarUsersBySpecialNeedType(specialNeedType, max);
			allSimilarUsers.addAll(userIds);
		}

		return allSimilarUsers;
	}
	
}
