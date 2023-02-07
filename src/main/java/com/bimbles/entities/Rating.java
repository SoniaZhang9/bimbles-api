package com.bimbles.entities;


import com.bimbles.utils.types.RatingValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserItemId.class)
@Table(name = "USER_ITEM_REVIEW")
public class Rating {
	@Id 
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private NormalUser user;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	@Column(nullable = false)
	private RatingValue value;
}
  