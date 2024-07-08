package br.com.HotelariaDegaspari.api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HotelariaDto {

	public HotelariaDto() {

	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private int id;

	@NotNull(message = "Nome não pode ser null")
	private String nome;

	@NotNull(message = "Local não pode ser null")
	private String local;
	
	@NotNull @Min(1) @Max(500)
	private Integer capacidade;

	private String cnpj;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
