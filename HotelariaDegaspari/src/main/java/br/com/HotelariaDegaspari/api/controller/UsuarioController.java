package br.com.HotelariaDegaspari.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.HotelariaDegaspari.domain.service.HotelariaService;
import br.com.HotelariaDegaspari.infrastructure.model.HotelariaModel;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/app")
public class UsuarioController {
	
	//private final HotelariaRepository repository;
	
	@Autowired
	private HotelariaService service;
	
	@GetMapping(value = "/listar")
	@ResponseBody
	public ResponseEntity<List<HotelariaModel>> ListarTodos(){
		
		List<HotelariaModel> hotel = service.listarTodosServices();
				
		return new ResponseEntity<List<HotelariaModel>>(hotel, HttpStatus.OK);
	}
	
	@GetMapping(value= "/unicoHotel/{id}")
	public ResponseEntity<HotelariaModel> Achar(@PathVariable int id){
		return service.acharIdService(id)
			   .map(mapeando -> ResponseEntity.ok().body(mapeando))
			   .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping(value="/salvar")
	@ResponseBody
	public ResponseEntity<HotelariaModel> salvar(@RequestBody HotelariaModel hotelaria){
		
		HotelariaModel hotel = service.salvarServices(hotelaria);
		
		return new ResponseEntity<HotelariaModel>(hotel, HttpStatus.CREATED);
	}
	
		
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> update(@PathVariable(value = "id") int id, @RequestBody HotelariaModel hotel) {
	Optional<HotelariaModel> hotelOptional = service.acharIdService(id);

	if (!hotelOptional.isPresent()) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel nao encontrado");
	}

	service.salvarServices(hotel);
	return ResponseEntity.status(HttpStatus.OK).body(hotel);

	}
	
	@DeleteMapping(value="/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable int id){
		return service.acharIdService(id)
			.map(mapeandoHotel -> { 
				service.deletarService(id);				
			    return ResponseEntity.ok().body("Hotel deletado com SUCESSO");
			}).orElse(ResponseEntity.notFound().build());
  
	}
}	
	
	

