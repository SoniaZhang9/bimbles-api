package com.bimbles.exception;

import java.io.Serial;

public class RatingAlreadyExistException extends RuntimeException {
	

	@Serial
	private static final long serialVersionUID = 1L;

	public RatingAlreadyExistException() {
		super("Ya existe una valoración hecha");
	}
}
