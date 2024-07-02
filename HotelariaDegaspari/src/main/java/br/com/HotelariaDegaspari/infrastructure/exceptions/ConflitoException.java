package br.com.HotelariaDegaspari.infrastructure.exceptions;

public class ConflitoException extends Exception{

	public ConflitoException(String erroConflict) {
		super(erroConflict);
	}
}
