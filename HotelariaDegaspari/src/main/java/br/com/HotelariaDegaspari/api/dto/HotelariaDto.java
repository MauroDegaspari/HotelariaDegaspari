package br.com.HotelariaDegaspari.api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelariaDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private int id;

	@NotNull(message = "Nome não pode ser null")
	private String nome;

	@NotNull(message = "Local não pode ser null")
	private String local;
	
	@NotNull @Min(1) @Max(500)
	private Integer capacidade;

	private String cnpj;

	

}
