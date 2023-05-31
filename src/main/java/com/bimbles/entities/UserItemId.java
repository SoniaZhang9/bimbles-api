package com.bimbles.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserItemId implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	private Long user;
	private Long item;
	
	@Override
	public int hashCode() {
		return Objects.hash(item, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserItemId other = (UserItemId) obj;
		return Objects.equals(item, other.item) && Objects.equals(user, other.user);
	}
}
