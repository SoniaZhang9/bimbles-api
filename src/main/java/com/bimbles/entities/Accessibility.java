package com.bimbles.entities;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Accessibility {
	private Boolean physical = false;
	private Boolean visual = false;
	private Boolean auditory = false;
}
