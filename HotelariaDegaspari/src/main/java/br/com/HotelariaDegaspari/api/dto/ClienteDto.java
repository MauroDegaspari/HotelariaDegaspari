package br.com.HotelariaDegaspari.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private int UUID;
	
	private String nome;
	
	private int cpf;
	
	private int fone;

}
