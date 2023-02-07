package com.bimbles.entities;


import com.bimbles.utils.types.BusinessType;
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
public class Business extends ItemPhysical {
	
	private BusinessType businessType;
}
