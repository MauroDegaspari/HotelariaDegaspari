package br.com.HotelariaDegaspari.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.HotelariaDegaspari.infrastructure.model.ClienteModel;

public interface ClienteRepository  extends JpaRepository<ClienteModel, UUID>{

}
