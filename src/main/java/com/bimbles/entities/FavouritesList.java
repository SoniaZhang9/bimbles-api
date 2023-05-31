package com.bimbles.entities;

import java.util.Set;
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

	@ManyToMany
	@Column(nullable = false)
	@Basic(fetch=FetchType.EAGER)
	@JoinTable(name="FAVOURITE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name="ITEM_ID"))
	private Set<Item> favsList;
}
