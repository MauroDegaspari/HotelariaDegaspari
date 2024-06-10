package br.com.HotelariaDegaspari.infrastructure.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder // faz com que toda a classe seja 'buildada'
@Entity

@Table(name = "Hotelaria_Model")
public class HotelariaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	
	private String nome;
	private String local;
	private String capacidade;
	private String cnpj;

	

}