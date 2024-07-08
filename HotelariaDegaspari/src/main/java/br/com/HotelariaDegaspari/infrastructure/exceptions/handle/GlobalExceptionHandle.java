package br.com.HotelariaDegaspari.infrastructure.exceptions.handle;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.HotelariaDegaspari.infrastructure.exceptions.BadRequestException;
import br.com.HotelariaDegaspari.infrastructure.exceptions.ConflitoException;
import br.com.HotelariaDegaspari.infrastructure.exceptions.ImprocessavelException;
import br.com.HotelariaDegaspari.infrastructure.exceptions.NegocioException;

@ControllerAdvice
public class GlobalExceptionHandle {

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<ErrosResponse> handleResponseEntity(NegocioException ex, HttpServletRequest request){		
		return response(ex.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());		
	}
	
	@ExceptionHandler(ConflitoException.class)
	public ResponseEntity<ErrosResponse> handleResponseEntity(ConflitoException cf, HttpServletRequest request){		
		return response(cf.getMessage(), request, HttpStatus.CONFLICT, LocalDateTime.now());		
	}
	
	@ExceptionHandler(ImprocessavelException.class)
	public ResponseEntity<ErrosResponse> handleResponseEntity(ImprocessavelException up, HttpServletRequest request){		
		return response(up.getMessage(), request, HttpStatus.UNPROCESSABLE_ENTITY, LocalDateTime.now());		
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrosResponse> handleResponseEntity(BadRequestException up, HttpServletRequest request){		
		return response(up.getMessage(), request, HttpStatus.BAD_REQUEST, LocalDateTime.now());		
	}
	
	private ResponseEntity<ErrosResponse> response(final String message, final HttpServletRequest request, final  HttpStatus status, LocalDateTime data ){
		return  ResponseEntity.status(status).body(new ErrosResponse(message, data, status.value(), request.getRequestURI()));
	}
}
