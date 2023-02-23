package com.bimbles.entities;



import jakarta.persistence.Column;
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
public class Image {
	@Column(name="IMG_NAME")
	private String name;
	
	@Column(name="IMG_TYPE")
	private String type;
	
	@Column(length = 500000000)
	private byte[] imgByte;
}
