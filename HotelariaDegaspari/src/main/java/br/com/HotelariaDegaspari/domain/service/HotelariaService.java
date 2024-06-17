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
		
	 return hotel; 
	}
	

	public HotelariaModel salvarServices(HotelariaModel hotel){
	 try {
		
		 if(this.validarHotel(hotel) == false){
			 return null;
		 }
		
	}catch (Exception e) {
			System.out.println("erro :" + e);
			JOptionPane.showConfirmDialog(null, "Erro: "+ e);
		} 
				
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
	
	public boolean validarHotel(HotelariaModel hotel){
	try {
		if (hotel.getNome().isEmpty() || hotel.getCnpj().isEmpty())
			 return false;
		 
		 if (hotel.getCnpj().length() != 14  )
			 return false;	
		 
		 if (hotel.getCapacidade() < 0)
			 return false;
		
	}catch (Exception e) {
		// TODO: handle exception
	}
		
		return true;
	}

}
