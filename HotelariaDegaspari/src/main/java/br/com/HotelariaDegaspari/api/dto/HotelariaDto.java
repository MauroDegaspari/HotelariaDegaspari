package br.com.HotelariaDegaspari.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;



public class HotelariaDto {
	
	public HotelariaDto() {
		
	}
	@JsonProperty
	private int id;
	
	@NotNull(message = "Nome não pode ser null")
	private String nome;
	
	@NotNull(message = "Local não pode ser null")
	private String local;
	
	@Size(min = 1, max = 100, message = "teste capacidade")
	private int capacidade;
	
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
	public int getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	
	
	

}
