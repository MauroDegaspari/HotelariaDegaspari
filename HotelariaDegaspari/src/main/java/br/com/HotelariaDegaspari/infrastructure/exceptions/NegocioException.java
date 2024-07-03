package br.com.HotelariaDegaspari.infrastructure.exceptions;


public class NegocioException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NegocioException(String erroBusiness) {
		super(erroBusiness);
	}
	
	public NegocioException(String erroUnprocessable, Throwable cause) {
		
		super(erroUnprocessable, cause);
	}

}
