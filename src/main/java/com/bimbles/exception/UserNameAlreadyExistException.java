package com.bimbles.exception;

import java.io.Serial;

public class UserNameAlreadyExistException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public UserNameAlreadyExistException(String userName) {
		super("El nombre de usuario " + userName + " ya existe." );
	}
}
