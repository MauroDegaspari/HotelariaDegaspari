package br.com.HotelariaDegaspari.domain.service;

import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

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
		if(hotel.isEmpty())
		
		System.out.print("Não existe nenhum hotel");
		
	 return hotel; 
	}
	

	public HotelariaModel salvarServices(HotelariaModel hotel){
		
		HotelariaModel novoHotel = repository.save(hotel);
		
	return novoHotel; 
	
	}
	public Optional<HotelariaModel> acharIdService(int hotel){
		
		Optional<HotelariaModel> hotelId = repository.findById(hotel);
		
		return hotelId;
		
	}
	
	public void deletarService(int id){
		
	repository.deleteById(id);
		
	}

}
