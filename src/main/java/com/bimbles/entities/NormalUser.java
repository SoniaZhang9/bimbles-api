package com.bimbles.entities;

import com.bimbles.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("NORMAL")
public class NormalUser extends User {
	@Embedded
	@JsonView(Views.NormalUserPreferences.class)
	private Preferences preferences;
	
	@Embedded
	@Column(nullable = false)
	@JsonView(Views.NormalUserFavourites.class)
	private FavouritesList favouritesList;

	@Embedded
	private Address address;
}
