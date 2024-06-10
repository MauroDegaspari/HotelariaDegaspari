package br.com.HotelariaDegaspari.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {
	
	@RequestMapping("/app")
	public String index() {
		return "Teste Web";
	}
}
