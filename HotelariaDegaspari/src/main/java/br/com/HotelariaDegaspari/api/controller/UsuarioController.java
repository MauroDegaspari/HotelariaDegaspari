package br.com.HotelariaDegaspari.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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
import br.com.HotelariaDegaspari.infrastructure.repository.HotelariaRepository;

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
	
		
	@PutMapping("/editar/{id}")
	public HotelariaModel editar(@PathVariable int id, @RequestBody HotelariaModel hotel ){
		HotelariaModel novoHotel = this.service.acharIdService(id).get();
		BeanUtils.copyProperties(hotel, novoHotel,"id");
		return service.salvarServices(novoHotel);
		
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
	
	

