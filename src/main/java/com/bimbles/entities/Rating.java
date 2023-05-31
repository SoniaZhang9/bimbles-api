package com.bimbles.entities;


import com.bimbles.utils.types.RatingValue;

import jakarta.persistence.*;
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	@Column(nullable = false)
	private RatingValue value;

	public Integer getOrdinalRatingValue() {
		return this.value.ordinal();
	}

	@Override
	public String toString() {
		return "\n user=" + user.getId() +
				", item=" + item.getId() +
				", value=" + value.ordinal();
	}
}
  