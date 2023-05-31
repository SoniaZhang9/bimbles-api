package com.bimbles.entities;

import com.bimbles.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@JsonView(Views.NormalUserData.class)
public class Address {
	private String street;
	private Integer flat;
	private Integer number;
	private Integer postalCode;
	private String city;
	private String province;
	private String country;
}
