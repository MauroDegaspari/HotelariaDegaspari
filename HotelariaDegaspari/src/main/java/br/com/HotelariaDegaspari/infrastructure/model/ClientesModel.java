package br.com.HotelariaDegaspari.infrastructure.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="TB_CLIENTES")
public class ClientesModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_cliente", nullable = false)
	private UUID id;
	
	private String nome;
	
	private int cpf;
	
	private int fone;
	
	@ManyToOne
	@JoinColumn(name = "id_hotel")
	private HotelariaModel hotel;

	public HotelariaModel getHotel() {
		return hotel;
	}

	public void setHotel(HotelariaModel hotel) {
		this.hotel = hotel;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}

	public int getFone() {
		return fone;
	}

	public void setFone(int fone) {
		this.fone = fone;
	}
	
	
	
}


