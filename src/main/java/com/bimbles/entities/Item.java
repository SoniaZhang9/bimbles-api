package com.bimbles.entities;

import java.net.URL;
import java.util.Set;

import com.bimbles.utils.Views;
import com.bimbles.utils.types.State;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "ITEM_TYPE")
@JsonView(Views.NormalUserFavourites.class)

@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.PROPERTY) // Include Java class simple-name as JSON property "type"
@JsonSubTypes({  @JsonSubTypes.Type(value = Restaurant.class), @JsonSubTypes.Type(value = Place.class),  @JsonSubTypes.Type(value = Product.class),  @JsonSubTypes.Type(value = Business.class)}) // Required for deserialization only
public abstract class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private State state;
	
	@Embedded
	@Column(nullable = false)
	private Address address;
	
	@Column(nullable = false)
	private String phoneNumber;
	private URL website;
	
	private String schedule;
	
	@Column(nullable = false, length = 1200)
	private String description;
	
	@Column(nullable = false)
	@Embedded
	@Basic(fetch=FetchType.EAGER)
	private Image thumbnail;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Embedded
	private Set<Image> pictures;
	
	private Float averageRating = (float) 0;
	
	private Integer nTotalRating = 0;
}
 