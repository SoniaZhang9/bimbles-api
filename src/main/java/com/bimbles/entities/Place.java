package com.bimbles.entities;


import com.bimbles.utils.types.PlaceType;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Place extends ItemPhysical {

	private PlaceType placeType;
}
