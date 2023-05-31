package com.bimbles.entities;

import java.net.URL;
import java.util.Objects;
import java.util.Set;

import com.bimbles.utils.Views;
import com.bimbles.utils.types.State;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.media.Schema;
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

// Adds new atribbute called "type". It can have one of these values: Place, Product, Restaurant or Business
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.PROPERTY) // Include Java class simple-name as JSON property "type"
@JsonSubTypes({  @JsonSubTypes.Type(value = Restaurant.class), @JsonSubTypes.Type(value = Place.class),  @JsonSubTypes.Type(value = Product.class),  @JsonSubTypes.Type(value = Business.class)}) // Required for deserialization only
public abstract class Item implements Comparable<Item>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private State state = State.REVIEWING;
	
	@Embedded
	private Address address;

	private String phoneNumber;
	@Schema( type = "url", example = "https://example.com")
	private URL website;
	
	private String schedule;
	
	@Column(nullable = false, length = 1200)
	private String description;
	
	@Column(nullable = false)
	@Embedded
	@Basic(fetch=FetchType.EAGER)
	@JsonView(Views.NormalUserFavourites.class)
	private Image thumbnail;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Image> pictures;
	
	private Float averageRating = (float) 0;
	
	private Integer nTotalRating = 0;

	@Override
	public String toString() {
		return "\n Item " + id + "- " + title;
	}

	@Override
	public int compareTo(Item o) {return this.id.compareTo(o.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item item)) return false;
		return getId().equals(item.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
 