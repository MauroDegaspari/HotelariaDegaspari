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

@RestController
//@RequiredArgsConstructor
@RequestMapping("/app")
public class UsuarioController {

	// private final HotelariaRepository repository;

	@Autowired
	private HotelariaService service;

	@GetMapping(value = "/listarTodos")
	@ResponseBody
	public ResponseEntity<List<HotelariaModel>> ListarTodos() {

		List<HotelariaModel> hotel = service.listarTodosServices();
		return hotel.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) 
						       : new ResponseEntity<List<HotelariaModel>>(hotel, HttpStatus.OK);

	}

	@GetMapping(value = "/unicoHotel/{id}")
	public ResponseEntity<HotelariaModel> Achar(@PathVariable int id) {
		return service.acharIdService(id).map(mapeando -> ResponseEntity.ok().body(mapeando))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping(value = "/salvar")
	@ResponseBody
	public ResponseEntity<Object> salvar(@RequestBody HotelariaModel hotelaria) {

		HotelariaModel hotel = service.salvarServices(hotelaria);

		return hotel == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Salvar:  o(╥﹏╥)o	(｡•́︿•̀｡)")
							 : ResponseEntity.status(HttpStatus.OK)
						.body("Hotel " + hotel.getNome() + " Salvo com Sucesso. (/^▽^)/... (ﾉﾟ0ﾟ)ﾉ~");

	}

	@PutMapping(value = "/editar/{id}")
	public ResponseEntity<Object> editar(@PathVariable(value = "id") int id, @RequestBody HotelariaModel hotel) {

		if (hotel.equals(null))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel nao encontrado (•ิ_•ิ) (•ิ_•ิ)");
		
		service.validarEdicaoHotel(id, hotel);
		
		return hotel == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Editar: (•ิ_•ิ) o(╥﹏╥)o	(｡•́︿•̀｡)")
							 : ResponseEntity.status(HttpStatus.OK)
						.body("Hotel " + hotel.getNome() + " Editado com Sucesso. (ﾉﾟ0ﾟ)ﾉ... (/^▽^)/");

	}

	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable int id) {
		return service.acharIdService(id).map(mapeandoHotel -> {
			service.deletarService(id);
			return ResponseEntity.ok().body("Hotel" + mapeandoHotel.getNome()+ "deletado com SUCESSO ");
		}).orElse(ResponseEntity.notFound().build());

	}

	/**
	 * 
	 * @apiNote Exercicio 5
	 * @since 18/06/2024 21:07:21
	 * @author Mauro Degaspari
	 * @return Trazendo hotel por CNPJ cadastrado
	 */
	@GetMapping(value = "/buscarCnpj/{cnpj}")
	public ResponseEntity<HotelariaModel> acharPeloCnpj(@PathVariable(value = "cnpj") String cnpj) {
		return service.acharHotelCnpj(cnpj).map(mapeandoCnpj -> ResponseEntity.ok().body(mapeandoCnpj))
				.orElse(ResponseEntity.notFound().build());

	}
}
