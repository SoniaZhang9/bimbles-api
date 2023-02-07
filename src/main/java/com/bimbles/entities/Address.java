package com.bimbles.entities;

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
public class Address {
	private String street;
	private Integer number;
	private Integer flat;
	private Integer postalCode;
	private String city; 
	//tal vez usar enumerados
	// https://datos.comunidad.madrid/catalogo/api/3/action/datastore_search?resource_id=ee750429-1e05-411a-b026-a57ea452a34a&fields=municipio_nombre&offset=100
	private String province;
}
