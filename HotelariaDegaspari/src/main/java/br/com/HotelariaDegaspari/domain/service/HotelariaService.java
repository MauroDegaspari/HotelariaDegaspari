package br.com.HotelariaDegaspari.domain.service;

import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.HotelariaDegaspari.domain.exception.ExceptionHotel;
import br.com.HotelariaDegaspari.infrastructure.model.HotelariaModel;
import br.com.HotelariaDegaspari.infrastructure.repository.HotelariaRepository;

@Service
public class HotelariaService {

	private final HotelariaRepository repository;

	@Autowired
	public HotelariaService(HotelariaRepository repository) {
		this.repository = repository;
	}

	public List<HotelariaModel> listarTodosServices() {
		List<HotelariaModel> hotel = repository.findAll();

		try {

			if (hotel.isEmpty())
				JOptionPane.showMessageDialog(null, "Não existe Hotel CADASTRADO. ");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());

		}

		return hotel;
	}

	public HotelariaModel salvarServices(HotelariaModel hotel) {
		try {

			if (this.validarHotel(hotel) == false) {
				return null;
			}

		} catch (Exception e) {
			System.out.println("erro :" + e);
			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
		}

		HotelariaModel novoHotel = repository.save(hotel);
		return novoHotel;

	}

	public Optional<HotelariaModel> acharIdService(int hotel) {

		Optional<HotelariaModel> hotelId = repository.findById(hotel);

		try {

			if (!hotelId.isPresent())
				throw new ExceptionHotel("Hotel Não encontrado por ID");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}

		return hotelId;

	}

	public Optional<HotelariaModel> acharHotelCnpj(String cnpj) {

		Optional<HotelariaModel> cnpjHotel = repository.acharCnpj(cnpj);

		try {
			if (!cnpjHotel.isPresent())
				throw new ExceptionHotel("Hotel Não encontrado por CNPJ");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}

		return cnpjHotel;

	}
	
	public Optional<HotelariaModel> acharHotelLocal(String local) {

		Optional<HotelariaModel> LocalidadeHotel = repository.AcharPorLocalidade(local);

		try {
			if (!LocalidadeHotel.isPresent())
				throw new ExceptionHotel("Hotel Não encontrado por CNPJ");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}

		return LocalidadeHotel;

	}
	

	public Optional<HotelariaModel> deletarService(int id) {

		Optional<HotelariaModel> deletarHotel = repository.findById(id);
		try {
			if (!deletarHotel.isPresent())
				throw new ExceptionHotel("Não existe o hotel para DELETAR");
		} catch (Exception e) {
			// TODO: handle exception
		}
		repository.delete(deletarHotel.get());
		return deletarHotel;
	}

	public boolean validarHotel(HotelariaModel hotel) throws Exception {

		List<HotelariaModel> todosHoteis = repository.findAll();

		for (HotelariaModel hotelariaModel : todosHoteis) {
			if (hotelariaModel.getCnpj().equals(hotel.getCnpj())) {
				throw new Exception("Hotel já existe!");
			}
		}

		if (hotel.getNome().isEmpty() || hotel.getCnpj().isEmpty())
			throw new Exception("campo vazio, nome ou cnpj");

		if (hotel.getCnpj().length() != 14) {
			throw new Exception("Erro: CNPJ deve conter 14 numeros");
		}

		if (hotel.getCapacidade() < 0) {

			throw new Exception("Capacidade tem que ser maior que 0");

		}

		return true;
	}

	public HotelariaModel validarEdicaoHotel(int id, HotelariaModel hotel) {

		HotelariaModel hotelNovo = acharIdService(id).get();

		try {

			BeanUtils.copyProperties(hotel, hotelNovo);
			salvarServices(hotelNovo);

		} catch (Exception e) {
			e.printStackTrace();
			new ExceptionHotel("Erro: Editar hotel");
		}
		return hotelNovo;
	}

}
