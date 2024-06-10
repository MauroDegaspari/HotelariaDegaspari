package br.com.HotelariaDegaspari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.HotelariaDegaspari.api.controller.UsuarioController;
import br.com.HotelariaDegaspari.infrastructure.model.HotelariaDegaspariModel;

@SpringBootApplication
public class HotelariaDegaspariApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelariaDegaspariApplication.class, args);
		
		UsuarioController usuario = new UsuarioController();
		usuario.index();
		

	}

}
