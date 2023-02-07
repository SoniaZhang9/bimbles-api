package com.bimbles.exception;

public class RatingAlreadyExistException extends RuntimeException {
	

	private static final long serialVersionUID = 1L;

	public RatingAlreadyExistException() {
		super("Ya existe una valoración hecha");
	}
}
