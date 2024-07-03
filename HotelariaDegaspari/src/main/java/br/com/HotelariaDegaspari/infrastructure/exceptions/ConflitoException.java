package br.com.HotelariaDegaspari.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflitoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ConflitoException(String message) {
		super(message);
	}
	
	public ConflitoException(String message, Throwable cause) {
		super(message, cause);
	}
}
