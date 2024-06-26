package br.com.HotelariaDegaspari.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.HotelariaDegaspari.api.dto.HotelariaDto;
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
	
	/**
	 * 
	 * @apiNote Exercicio 7
	 * @since 25/06/2024 22:27:21
	 * @author Mauro Degaspari
	 * @return Faça o metodo update e o de listagem fazendo com que seja retornado para o usuario como DTO.
	 *		   Requisitos:
	 *		   Criar o metodo de listar e update tendo como retorno DTO;
	 *		   Tendo no controller apenas DTO;
	 *		
	 *		   use todo o seu conhecimento (Vá além)
	 * 
	 */
	@Transactional(readOnly = true)
	public List<HotelariaDto> listarTodosHoteisServices() {
		
		List<HotelariaModel> hotelModel = repository.findAll();

		try {

			if (hotelModel.isEmpty())
				JOptionPane.showMessageDialog(null, "Não existe Hotel CADASTRADO. ");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());

		}

		return listaParaDto(hotelModel);
	}

	@Transactional
	public HotelariaDto salvarServices(HotelariaDto hotelDto) {
		
		HotelariaModel model = paraModel(hotelDto);
		
		try {

			if (this.validarHotel(model) == false) {
				return null;
			}

		} catch (Exception e) {
			System.out.println("erro :" + e);
			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
		}

		HotelariaModel novoHotel = repository.save(model);
		return paraDto(novoHotel);

	}
	
	@Transactional(readOnly = true)
	public Optional<HotelariaModel> acharIdService(int hotel) {

		Optional<HotelariaModel> hotelId = repository.findById(hotel);

		try {

			if (!hotelId.isPresent())
				throw new ExceptionHotel("Hotel Não encontrado");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}

		return hotelId;

	}
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public Optional<HotelariaModel> acharHotelLocal(String local) {

		Optional<HotelariaModel> LocalidadeHotel = repository.AcharPorLocalidade(local);

		try {
			if (!LocalidadeHotel.isPresent())
				throw new ExceptionHotel("Hotel Não encontrado por LOCALIZAÇÃO");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}

		return LocalidadeHotel;

	}
	
	@Transactional
	public Optional<HotelariaModel> deletarService(int id) {

		Optional<HotelariaModel> deletarHotel = repository.findById(id);
			
		repository.delete(deletarHotel.get());
		return deletarHotel;
	}

	public boolean validarHotel(HotelariaModel hotel) throws Exception {

		List<HotelariaModel> todosHoteis = repository.findAll();

		for (HotelariaModel hotelariaModel : todosHoteis) {
			if (hotelariaModel.getCnpj().equals(hotel.getCnpj())) {
				throw new Exception("Hotel com CNPJ " + hotelariaModel.getCnpj() +" já existe!");
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

	private HotelariaDto paraDto(HotelariaModel model) {
		HotelariaDto dto = new HotelariaDto();
		BeanUtils.copyProperties(model, dto );
		return dto;
	}
	
	private HotelariaModel paraModel(HotelariaDto dto) {
		HotelariaModel model = new HotelariaModel();
		BeanUtils.copyProperties(dto, model);
		return model;
	}
	
	private List<HotelariaDto> listaParaDto(List<HotelariaModel> listaModel){
		return listaModel.stream().map(this::paraDto).collect(Collectors.toList());
	}
	
	
	public HotelariaDto validarEdicaoHotel(int id, HotelariaDto hotel) {

		HotelariaModel hotelNovo = acharIdService(id).get();

		try {

			BeanUtils.copyProperties(hotel, hotelNovo);
			repository.save(hotelNovo);
			//salvarServices(hotelNovo);

		} catch (Exception e) {
			e.printStackTrace();
			new ExceptionHotel("Erro: Editar hotel");
		}
		return paraDto(hotelNovo);
	}

}
