package br.com.HotelariaDegaspari.api.controller;

import java.util.List;

import javax.validation.Valid;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/app")
public class HotelariaController {

	@Autowired
	private HotelariaService service;

	@Operation(summary = "Salvar novos Hoteis", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Hotel salvo com Sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao salvar Hotel"),
			@ApiResponse(responseCode = "422", description = "Erro ???"),
			@ApiResponse(responseCode = "400", description = "Erro 400")})
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

	@Operation(summary = "Salvar novos Hoteis", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Hotel salvo com Sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao salvar Hotel"),
			@ApiResponse(responseCode = "409", description = "Erro ???"),
			@ApiResponse(responseCode = "400", description = "Erro 400")})
	@PostMapping(value = "/salvar")
	public ResponseEntity<String> salvar(@Valid @RequestBody HotelariaDto hotelaria) {

		HotelariaDto hotel = service.salvarServices(hotelaria);

		return hotel == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Salvar: ")
				: ResponseEntity.status(HttpStatus.OK).body("Hotel " + hotel.getNome() + " Salvo com Sucesso. ");

	}

	@PutMapping(value = "/editar/{id}")
	public ResponseEntity<String> editar(@PathVariable(value = "id") int id, @RequestBody HotelariaDto hotel) {

		if (hotel.equals(null))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel nao encontrado");

		return service.validarEdicaoHotel(id, hotel) == null
				? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Editar:")
				: ResponseEntity.status(HttpStatus.OK).body("Hotel " + hotel.getNome() + " Editado com Sucesso.");

	}

	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<String> deletar(@PathVariable int id) {
		return service.deletarService(id) == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Editar:")
				: ResponseEntity.status(HttpStatus.OK).body("Hotel Deletado.");

	}

	@GetMapping(value = "/buscarCnpj/{cnpj}")
	public ResponseEntity<HotelariaDto> acharPeloCnpj(@PathVariable(value = "cnpj") String cnpj) {
		return service.acharHotelCnpj(cnpj).map(mapeandoCnpj -> ResponseEntity.ok().body(mapeandoCnpj))
				.orElse(ResponseEntity.notFound().build());

	}

	@GetMapping(value = "/buscarLocalidade/{local}")
	public ResponseEntity<List<HotelariaDto>> acharPorLocalidade(@PathVariable(value = "local") String local) {
		return service.acharHotelLocal(local).map(mapeandoLocaidade -> ResponseEntity.ok().body(mapeandoLocaidade))
				.orElse(ResponseEntity.notFound().build());

	}
}
