package br.com.HotelariaDegaspari.api.controller;

import java.util.List;
import java.util.Optional;

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

@RestController
//@RequiredArgsConstructor
@RequestMapping("/app")
public class UsuarioController {
	
	//private final HotelariaRepository repository;
	
	@Autowired
	private HotelariaService service;
	
	@GetMapping(value = "/listarTodos")
	@ResponseBody
	public ResponseEntity<List<HotelariaModel>> ListarTodos(){
		
		List<HotelariaModel> hotel = service.listarTodosServices();
		return hotel.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) :
			                     new ResponseEntity<List<HotelariaModel>>(hotel, HttpStatus.OK);
				
		
	}
	
	@GetMapping(value= "/unicoHotel/{id}")
	public ResponseEntity<HotelariaModel> Achar(@PathVariable int id){
		return service.acharIdService(id)
			   .map(mapeando -> ResponseEntity.ok().body(mapeando))
			   .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping(value="/salvar")
	@ResponseBody
	public ResponseEntity<Object> salvar(@RequestBody HotelariaModel hotelaria){
		
		HotelariaModel hotel = service.salvarServices(hotelaria);
		
		return hotel == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Salvar: Nome e(ou) CNPJ deve ser preenchidos" ):
						       ResponseEntity.status(HttpStatus.OK).body("Hotel " + hotel.getNome() +" Salvo com Sucesso.");	

	}
	
		
	@PutMapping(value = "/editar/{id}")
	public ResponseEntity<Object> editar(@PathVariable(value = "id") int id, @RequestBody HotelariaModel hotel) {
	HotelariaModel hotelNovo = service.acharIdService(id).get();

	if (hotelNovo == null)
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel nao encontrado");
	
	BeanUtils.copyProperties(hotel, hotelNovo,"id");
	service.salvarServices(hotelNovo);
	return hotel == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Salvar: Nome e(ou) CNPJ deve ser preenchidos" ):
	                       ResponseEntity.status(HttpStatus.OK).body("Hotel " + hotel.getNome() +" Editado com Sucesso.");	


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
	
	

