package com.bimbles.services;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private ItemRepository itemRepository;
	
	public List<Item> findAll(Pageable page) {
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
	
}
