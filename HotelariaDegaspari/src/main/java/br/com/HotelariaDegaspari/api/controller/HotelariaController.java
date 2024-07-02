package br.com.HotelariaDegaspari.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.HotelariaDegaspari.api.dto.HotelariaDto;
import br.com.HotelariaDegaspari.domain.service.HotelariaService;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/app")
public class HotelariaController {

	// private final HotelariaRepository repository;

	@Autowired
	private HotelariaService service;

	/**
	 * 
	 * @apiNote Exercicio 7
	 * @since 25/06/2024 22:27:21
	 * @author Mauro Degaspari
	 * @return Faça o metodo update e o de listagem fazendo com que seja retornado
	 *         para o usuario como DTO. Requisitos: Criar o metodo de listar e
	 *         update tendo como retorno DTO; Tendo no controller apenas DTO;
	 * 
	 *         use todo o seu conhecimento (Vá além)
	 * 
	 */
	@GetMapping(value = "/listarTodos")
	public ResponseEntity<List<HotelariaDto>> ListarTodos() {

		List<HotelariaDto> hotel = service.listarTodosHoteisServices();
		return hotel.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
				: new ResponseEntity<List<HotelariaDto>>(hotel, HttpStatus.OK);

	}

	@GetMapping(value = "/unicoHotel/{id}")
	public ResponseEntity<HotelariaDto> Achar(@PathVariable int id) {
		return service.acharIdService(id).map(mapeando -> ResponseEntity.ok().body(mapeando))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping(value = "/salvar")
	public ResponseEntity<String> salvar(@RequestBody HotelariaDto hotelaria) {

		HotelariaDto hotel = service.salvarServices(hotelaria);

		return hotel == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Salvar: ")
				: ResponseEntity.status(HttpStatus.OK).body("Hotel " + hotel.getNome() + " Salvo com Sucesso. ");

	}

	/**
	 * 
	 * @apiNote Exercicio 7
	 * @since 25/06/2024 22:27:21
	 * @author Mauro Degaspari
	 * @return Faça o metodo update e o de listagem fazendo com que seja retornado
	 *         para o usuario como DTO. Requisitos: Criar o metodo de listar e
	 *         update tendo como retorno DTO; Tendo no controller apenas DTO;
	 * 
	 *         use todo o seu conhecimento (Vá além)
	 * 
	 */
	@PutMapping(value = "/editar/{id}")
	public ResponseEntity<String> editar(@PathVariable(value = "id") int id, @RequestBody HotelariaDto hotel) {

		if (hotel.equals(null))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel nao encontrado");

		return service.validarEdicaoHotel(id, hotel) == null
				? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Editar:")
				: ResponseEntity.status(HttpStatus.OK).body("Hotel " + hotel.getNome() + " Editado com Sucesso.");

	}

	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable int id) {
		return service.acharIdService(id).map(mapeandoHotel -> {
			service.deletarService(id);
			return ResponseEntity.ok().body("Hotel " + mapeandoHotel.getNome() + " deletado com SUCESSO ");
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
	public ResponseEntity<HotelariaDto> acharPeloCnpj(@PathVariable(value = "cnpj") String cnpj) {
		return service.acharHotelCnpj(cnpj).map(mapeandoCnpj -> ResponseEntity.ok().body(mapeandoCnpj))
				.orElse(ResponseEntity.notFound().build());

	}

	/**
	 * 
	 * @apiNote Exercicio 6
	 * @since 21/06/2024 21:07:21
	 * @author Mauro Degaspari
	 * @return Crie outro metodo personalizado. Podendo ser uma nova
	 *         'buscaPorAlgumaCoisa', podendo ser um novo 'deletarPorAlgumaCoisa',
	 *         'updatePorAlgumaCoisa'. Faça de acordo como foi feito em aula.
	 */
	@GetMapping(value = "/buscarLocalidade/{local}")
	public ResponseEntity<List<HotelariaDto>> acharPorLocalidade(@PathVariable(value = "local") String local) {
		return service.acharHotelLocal(local).map(mapeandoLocaidade -> ResponseEntity.ok().body(mapeandoLocaidade))
				.orElse(ResponseEntity.notFound().build());

	}
}
