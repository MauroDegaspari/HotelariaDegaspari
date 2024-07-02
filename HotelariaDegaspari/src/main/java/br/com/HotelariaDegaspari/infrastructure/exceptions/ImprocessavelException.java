package br.com.HotelariaDegaspari.infrastructure.exceptions;

public class ImprocessavelException extends Exception {
	
	public ImprocessavelException(String erroUnprocessable) {
		super(erroUnprocessable);
	}

}
