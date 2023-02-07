package com.bimbles.exception;

public class UserNameAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNameAlreadyExistException(String userName) {
		super("El nombre de usuario " + userName + " ya existe." );
	}
}
