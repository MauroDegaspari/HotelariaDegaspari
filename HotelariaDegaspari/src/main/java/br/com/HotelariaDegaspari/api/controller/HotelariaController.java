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
			@ApiResponse(responseCode = "500", description = "Erro Internal Server Error."),
			@ApiResponse(responseCode = "409", description = "Erro de conflito "),
			@ApiResponse(responseCode = "400", description = "Erro BAD REQUEST") })
	@PostMapping(value = "/salvar")
	public ResponseEntity<String> salvar(@Valid @RequestBody HotelariaDto hotelaria) {

		HotelariaDto hotel = service.salvarServices(hotelaria);

		return hotel == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Salvar: ")
				: ResponseEntity.status(HttpStatus.OK).body("Hotel " + hotel.getNome() + " Salvo com Sucesso. ");

	}

	@Operation(summary = "Editar Hotel", method = "PUT")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Hotel salvo com Sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro Internal Server Error."),
			@ApiResponse(responseCode = "409", description = "Erro de conflito "),
			@ApiResponse(responseCode = "400", description = "Erro BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "Erro NOTFOUND")})
	@PutMapping(value = "/editar/{id}")
	public ResponseEntity<String> editar(@PathVariable(value = "id") int id, @RequestBody HotelariaDto hotel) {

		if (hotel.equals(null))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel nao encontrado");

		return service.validarEdicaoHotel(id, hotel) == null
				? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Editar:")
				: ResponseEntity.status(HttpStatus.OK).body("Hotel " + hotel.getNome() + " Editado com Sucesso.");

	}

	@Operation(summary = "Deletar Hotel", method = "DELETE")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Hotel salvo com Sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro Internal Server Error."),
			@ApiResponse(responseCode = "409", description = "Erro de conflito "),
			@ApiResponse(responseCode = "400", description = "Erro BAD REQUEST") })
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<String> deletar(@PathVariable int id) {
		return service.deletarService(id) == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao Editar:")
				: ResponseEntity.status(HttpStatus.OK).body("Hotel Deletado.");

	}
	
	@Operation(summary = "Pesquisar hotel pelo ID", method = "PUT")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Hotel salvo com Sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro Internal Server Error."),
			@ApiResponse(responseCode = "409", description = "Erro de conflito "),
			@ApiResponse(responseCode = "400", description = "Erro BAD REQUEST")})
	@GetMapping(value = "/unicoHotel/{id}")
	public ResponseEntity<HotelariaDto> Achar(@PathVariable int id) {
		return service.acharIdService(id).map(mapeando -> ResponseEntity.ok().body(mapeando))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@Operation(summary = "Lista todos os Hoteis", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Hotel salvo com Sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao salvar Hotel"),
			@ApiResponse(responseCode = "422", description = "Erro UNPROCESSABLE_ENTITY - sintaxe da requisição esta correta, mas não foi possível processar as instruções presentes"),
			@ApiResponse(responseCode = "400", description = "Erro 400") })
	@GetMapping(value = "/listarTodos")
	public ResponseEntity<List<HotelariaDto>> ListarTodos() {

		List<HotelariaDto> hotel = service.listarTodosHoteisServices();
		return hotel.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
				: new ResponseEntity<List<HotelariaDto>>(hotel, HttpStatus.OK);

	}

	@Operation(summary = "Pesquisar por CNPJ", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Hotel salvo com Sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro Internal Server Error."),
			@ApiResponse(responseCode = "409", description = "Erro de conflito "),
			@ApiResponse(responseCode = "400", description = "Erro BAD REQUEST") })
	@GetMapping(value = "/buscarCnpj/{cnpj}")
	public ResponseEntity<HotelariaDto> acharPeloCnpj(@PathVariable(value = "cnpj") String cnpj) {
		return service.acharHotelCnpj(cnpj).map(mapeandoCnpj -> ResponseEntity.ok().body(mapeandoCnpj))
				.orElse(ResponseEntity.notFound().build());

	}

	@Operation(summary = "Pesquisar Hotel por LOCALIDADE", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Hotel salvo com Sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro Internal Server Error."),
			@ApiResponse(responseCode = "409", description = "Erro de conflito "),
			@ApiResponse(responseCode = "422", description = "Erro UNPROCESSABLE_ENTITY - sintaxe da requisição esta correta, mas não foi possível processar as instruções presentes"),
			@ApiResponse(responseCode = "400", description = "Erro BAD REQUEST") })
	@GetMapping(value = "/buscarLocalidade/{local}")
	public ResponseEntity<List<HotelariaDto>> acharPorLocalidade(@PathVariable(value = "local") String local) {
		return service.acharHotelLocal(local).map(mapeandoLocaidade -> ResponseEntity.ok().body(mapeandoLocaidade))
				.orElse(ResponseEntity.notFound().build());

	}
}
