package com.bimbles.entities;

import java.util.Set;

import com.bimbles.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouritesList {
	@JsonView(Views.NormalUserFavourites.class)
	@ManyToMany
	@Column(nullable = false)
	@JoinTable(name="FAVOURITE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name="ITEM_ID"))
	private Set<Item> favsList;
}
