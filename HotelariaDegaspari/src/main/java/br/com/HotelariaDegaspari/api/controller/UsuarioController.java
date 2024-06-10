package br.com.HotelariaDegaspari.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.HotelariaDegaspari.infrastructure.model.HotelariaModel;
import br.com.HotelariaDegaspari.infrastructure.repository.HotelariaRepository;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/app")
public class UsuarioController {
	
	//private final HotelariaRepository repository;
	@Autowired
	private HotelariaRepository repository;
	
	@GetMapping("/listar")
	public List<HotelariaModel> todos(){
		return repository.findAll();
	}
	
	@GetMapping(value= "/(id)")
	public ResponseEntity<HotelariaModel> Achar(@PathVariable UUID id){
		return repository.findById(id)
			   .map(mapeando -> ResponseEntity.ok().body(mapeando))
			   .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/salvar")
	public HotelariaModel salvar(@RequestBody HotelariaModel hotelaria){
		return repository.save(hotelaria);
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable UUID id){
		repository.deleteById(id);
	}
	
	@PostMapping("/editar")
	public HotelariaModel editar(@PathVariable UUID id, @RequestBody HotelariaModel hotel ){
		HotelariaModel novoHotel = this.repository.findById(id).get();
		BeanUtils.copyProperties(hotel, novoHotel,"id");
		return repository.save(novoHotel);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
