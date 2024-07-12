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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="TB_CLIENTES")
public class ClienteModel {
	
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

	
}


