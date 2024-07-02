package br.com.HotelariaDegaspari.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.HotelariaDegaspari.infrastructure.model.ClientesModel;
import br.com.HotelariaDegaspari.infrastructure.repository.ClientesRepository;

@RestController
@RequestMapping(value="/clientes")
public class ClientesContoller {
	
	@Autowired
	private ClientesRepository repo;
	
	@PostMapping(value="/cadastro")
	public ResponseEntity<?> salvarCliente(@RequestBody ClientesModel cliente){
		
		repo.save(cliente);
		
		return ResponseEntity.status(HttpStatus.OK).body("CLiente salvo");
	}
	
	@GetMapping(value = "/listarClientes")
	public ResponseEntity<List<ClientesModel>> ListarClientes(){
		
		List<ClientesModel> clientes = repo.findAll();
		
		return clientes.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null):
									new ResponseEntity<List<ClientesModel>>(clientes, HttpStatus.OK);
	}

}
