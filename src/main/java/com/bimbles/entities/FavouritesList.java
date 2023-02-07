package com.bimbles.entities;

import java.util.Set;

import com.bimbles.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class FavouritesList {
	@JsonView(Views.NormalUserFavourites.class)
	@ManyToMany
	@Column(nullable = false)
	@JoinTable(name="FAVOURITE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name="ITEM_ID"))
	private Set<Item> favsList;
}
