package br.com.HotelariaDegaspari.domain.service;

import java.util.ArrayList;
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
	 * @return Faça o metodo update e o de listagem fazendo com que seja retornado
	 *         para o usuario como DTO. Requisitos: Criar o metodo de listar e
	 *         update tendo como retorno DTO; Tendo no controller apenas DTO;
	 * 
	 *         use todo o seu conhecimento (Vá além)
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
	public Optional<HotelariaDto> acharIdService(int hotel) {

		Optional<HotelariaModel> hotelId = repository.findById(hotel);

		try {

			if (!hotelId.isPresent())
				throw new ExceptionHotel("Hotel Não encontrado na base de dados");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}
		
		HotelariaDto cnpjAcharId = new HotelariaDto();
		BeanUtils.copyProperties(hotelId.get(), cnpjAcharId);
		
		return Optional.of(cnpjAcharId);

	}

	@Transactional(readOnly = true)
	public Optional<HotelariaDto> acharHotelCnpj(String cnpj) {

		Optional<HotelariaModel> cnpjHotel = repository.acharCnpj(cnpj);

		try {
			if (!cnpjHotel.isPresent())
				throw new ExceptionHotel("Hotel Não encontrado por CNPJ");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}

		HotelariaDto cnpjDto = new HotelariaDto();
		BeanUtils.copyProperties(cnpjHotel.get(), cnpjDto);// .get() - Se um valor estiver presente neste Opcional,
															// retorna o valor, caso contrário lança
															// NoSuchElementException.

		return Optional.of(cnpjDto);// Optional.of - Retorna um Opcional com o valor presente não nulo especificado.

	}

	@Transactional(readOnly = true)
	public Optional<HotelariaDto> acharHotelLocal(String local) {

		Optional<HotelariaModel> localidadeHotel = repository.AcharPorLocalidade(local);

		try {
			if (!localidadeHotel.isPresent())
				throw new ExceptionHotel("Hotel Não encontrado por LOCALIZAÇÃO");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}

		HotelariaDto dtoLocalidade = new HotelariaDto();
		BeanUtils.copyProperties(localidadeHotel.get(), dtoLocalidade); // .get() - Se um valor estiver presente neste
																		// Opcional, retorna o valor, caso contrário
																		// lança NoSuchElementException.

		return Optional.of(dtoLocalidade); // Optional.of - Retorna um Opcional com o valor presente não nulo
											// especificado.

	}

	@Transactional
	public Optional<HotelariaDto> deletarService(int id) {

		Optional<HotelariaModel> deletarHotel = repository.findById(id);
		HotelariaDto dtoDelete = new HotelariaDto();

		try {
			if (deletarHotel.isPresent()) {
				BeanUtils.copyProperties(deletarHotel, dtoDelete);
				repository.delete(deletarHotel.get());
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.of(dtoDelete);
	}

	public boolean validarHotel(HotelariaModel hotel) throws Exception {

		List<HotelariaModel> todosHoteis = repository.findAll();

		for (HotelariaModel hotelariaModel : todosHoteis) {
			if (hotelariaModel.getCnpj().equals(hotel.getCnpj())) {
				throw new Exception("Hotel com CNPJ " + hotelariaModel.getCnpj() + " já existe!");
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
		BeanUtils.copyProperties(model, dto);
		return dto;
	}

	private HotelariaModel paraModel(HotelariaDto dto) {
		HotelariaModel model = new HotelariaModel();
		BeanUtils.copyProperties(dto, model);
		return model;
	}

	private List<HotelariaDto> listaParaDto(List<HotelariaModel> listaModel) {

		List<HotelariaDto> listaDto = new ArrayList<>();

		for (HotelariaModel model : listaModel) {
			HotelariaDto dto = paraDto(model);
			listaDto.add(dto);
		}

		return listaDto;

		// return listaModel.stream().map(model ->
		// paraDto(model)).collect(Collectors.toList());
		// return listaModel.stream().map(this::paraDto).collect(Collectors.toList())
	}

	public HotelariaDto validarEdicaoHotel(int id, HotelariaDto hotel) {

		HotelariaModel hotelNovo = paraModel(acharIdService(id).get());

		try {

			if (!hotelNovo.getCnpj().equals(hotel.getCnpj()))
				throw new ExceptionHotel("CNPJ não pode ser alterado.");

			BeanUtils.copyProperties(hotel, hotelNovo);
			repository.save(hotelNovo);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return paraDto(hotelNovo);
	}

}
