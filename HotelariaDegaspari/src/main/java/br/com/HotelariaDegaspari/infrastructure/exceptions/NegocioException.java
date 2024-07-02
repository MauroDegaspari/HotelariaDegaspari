package br.com.HotelariaDegaspari.infrastructure.exceptions;

public class NegocioException extends Exception{
	
	public NegocioException(String erroBusiness) {
		super(erroBusiness);
	}

}
