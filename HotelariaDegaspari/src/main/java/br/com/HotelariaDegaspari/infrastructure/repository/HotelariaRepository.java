package br.com.HotelariaDegaspari.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.HotelariaDegaspari.infrastructure.model.HotelariaModel;

@Repository
public interface HotelariaRepository extends JpaRepository<HotelariaModel, Integer>{

	
}
