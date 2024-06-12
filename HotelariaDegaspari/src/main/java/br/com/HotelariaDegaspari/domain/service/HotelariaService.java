package br.com.HotelariaDegaspari.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.HotelariaDegaspari.infrastructure.model.HotelariaModel;
import br.com.HotelariaDegaspari.infrastructure.repository.HotelariaRepository;

@Service
public class HotelariaService {
	
	@Autowired
	private HotelariaRepository repository;
	
	public List<HotelariaModel> listarTodosServices(){
		
		List<HotelariaModel> hotel = repository.findAll();
		
	return hotel; 
	}
	

	public HotelariaModel salvarServices(HotelariaModel hotel){
		
		HotelariaModel novoHotel = repository.save(hotel);
		
	return novoHotel; 
	
	}	

}
