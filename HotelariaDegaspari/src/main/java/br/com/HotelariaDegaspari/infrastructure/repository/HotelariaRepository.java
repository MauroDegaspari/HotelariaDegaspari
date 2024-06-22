package br.com.HotelariaDegaspari.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.HotelariaDegaspari.infrastructure.model.HotelariaModel;

@Repository
public interface HotelariaRepository extends JpaRepository<HotelariaModel, Integer>{

	@Query(value = "SELECT * FROM TB_HOTELARIA h WHERE h.CNPJ = :cnpj ", nativeQuery = true)
	Optional<HotelariaModel> acharCnpj(@Param("cnpj")String cnpj);
	
	@Query(value = "SELECT * FROM TB_HOTELARIA h WHERE Upper(h.LOCAL) LIKE Upper('%:local%') ", nativeQuery = true)
	//@Query(value = "SELECT * FROM TB_HOTELARIA  WHERE LOCAL = :local", nativeQuery = true)
	Optional<HotelariaModel> AcharPorLocalidade(@Param("local") String local);
}
