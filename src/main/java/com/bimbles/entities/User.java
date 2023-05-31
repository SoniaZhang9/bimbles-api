package com.bimbles.entities;

import java.util.Objects;

import com.bimbles.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ROLE")
public abstract class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.NormalUserData.class)
	private Long id;
	
	@Column(nullable = false)
	@JsonView(Views.NormalUserData.class)
	private String userName;
	
	@Column(nullable = false)
	@JsonView(Views.NormalUserData.class)
	private String password;

	@Column(nullable = false)
	@JsonView(Views.NormalUserData.class)
	private String name;
	
	@Column(nullable = false)
	@JsonView(Views.NormalUserData.class)
	private String surname;

	@Override
	public int hashCode() {
		return Objects.hash(id, name, password, surname, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(surname, other.surname)
				&& Objects.equals(userName, other.userName);
	}
	
}
