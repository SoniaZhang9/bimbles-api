package com.bimbles.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {

	@ExceptionHandler({UserNameAlreadyExistException.class, RatingAlreadyExistException.class})
    public ResponseEntity<String> exceptionHandler(RuntimeException ex){
       return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST );
    }
	
	@ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> exceptiondHandler(BadCredentialsException ex){
       return new ResponseEntity<String>("Usuario o contraseña incorrectos.", HttpStatus.BAD_REQUEST );
    }
	
	@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> exceptiondHandler(AccessDeniedException ex){
       return new ResponseEntity<String>("Acceso denegado.", HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> exceptiondHandler(EntityNotFoundException ex){
       return new ResponseEntity<String>("No existe dicho recurso.", HttpStatus.BAD_REQUEST);
    }
	
}
