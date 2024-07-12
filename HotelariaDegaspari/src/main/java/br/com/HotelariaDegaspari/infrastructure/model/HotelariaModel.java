package br.com.HotelariaDegaspari.infrastructure.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "TB_HOTELARIA")
public class HotelariaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_HOTEL", nullable = false)
	private int id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "local")
	private String local;
	private Integer capacidade;

	@Column(name = "CNPJ")
	private String cnpj;


}