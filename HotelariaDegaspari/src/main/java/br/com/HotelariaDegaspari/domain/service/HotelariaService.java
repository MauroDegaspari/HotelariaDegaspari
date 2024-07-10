package br.com.HotelariaDegaspari.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.HotelariaDegaspari.api.dto.HotelariaDto;
import br.com.HotelariaDegaspari.domain.conversoes.HotelariaConversao;
import br.com.HotelariaDegaspari.infrastructure.exceptions.BadRequestException;
import br.com.HotelariaDegaspari.infrastructure.exceptions.ConflitoException;
import br.com.HotelariaDegaspari.infrastructure.exceptions.ImprocessavelException;
import br.com.HotelariaDegaspari.infrastructure.exceptions.NegocioException;
import br.com.HotelariaDegaspari.infrastructure.exceptions.NotFoundException;
import br.com.HotelariaDegaspari.infrastructure.model.HotelariaModel;
import br.com.HotelariaDegaspari.infrastructure.repository.HotelariaRepository;

@Service
public class HotelariaService {

	private final HotelariaRepository repository;
	private final HotelariaConversao conversao;

	@Autowired
	public HotelariaService(HotelariaRepository repository, HotelariaConversao conversao) {
		this.repository = repository;
		this.conversao = conversao;
	}

	@Transactional(readOnly = true)
	public List<HotelariaDto> listarTodosHoteisServices() {

		List<HotelariaModel> hotelModel = repository.findAll();

		try {

			if (hotelModel.isEmpty())
				throw new ImprocessavelException("Não Existe Hotel para Listar");

		} catch (ImprocessavelException imp) {
			throw new ImprocessavelException(imp.getMessage());
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage());
		}

		return conversao.listaParaDto(hotelModel);
	}

	@Modifying
	@Transactional
	public HotelariaDto salvarServices(HotelariaDto hotelDto) {

		HotelariaModel model = conversao.paraModel(hotelDto);

		try {

			if (validarHotel(hotelDto) == false) {
				throw new BadRequestException("erro.");
			}

			HotelariaModel novoHotel = repository.save(model);
			return conversao.paraDto(novoHotel);

		} catch (BadRequestException e) {
			throw new BadRequestException(e.getMessage());
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public Optional<HotelariaDto> acharIdService(int hotel) {

		Optional<HotelariaModel> hotelId = repository.findById(hotel);

		try {

			if (!hotelId.isPresent())
				throw new NegocioException("Hotel Não encontrado na base de dados");

		} catch (Exception e) {

			e.printStackTrace();
			throw new NegocioException(e.getMessage());
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
				throw new ImprocessavelException("Hotel Não encontrado por CNPJ");

		} catch (Exception e) {

			e.printStackTrace();
			throw new ImprocessavelException(e.getMessage());
		}

		HotelariaDto cnpjDto = new HotelariaDto();
		BeanUtils.copyProperties(cnpjHotel.get(), cnpjDto);

		return Optional.of(cnpjDto);

	}

	@Transactional(readOnly = true)
	public Optional<List<HotelariaDto>> acharHotelLocal(String local) {

		List<Optional<HotelariaModel>> localidadeHotel = repository.AcharPorLocalidade(local);
		List<HotelariaDto> dtoLocalidade = new ArrayList<>();

		try {

			if (localidadeHotel.isEmpty()) {
				throw new ImprocessavelException("Nenhum Hotel encontrado pela pesquisa:");
			} else {
				for (Optional<HotelariaModel> LocalNovo : localidadeHotel) {

					HotelariaDto dto = new HotelariaDto();
					BeanUtils.copyProperties(LocalNovo.get(), dto); // .get() -
																	// Se um
																	// valor
																	// estiver
																	// presente
																	// neste
																	// Opcional,
																	// retorna o
																	// valor,
																	// caso
																	// contrário

					dtoLocalidade.add(dto);

				}

			}

			return Optional.of(dtoLocalidade); // Optional.of - Retorna um
												// Opcional com o valor presente
												// não nulo
												// especificado.

		} catch (ImprocessavelException e) {
			e.printStackTrace();
			throw new ImprocessavelException(e.getMessage());
		}

	}

	@Modifying
	@Transactional
	public Optional<HotelariaDto> deletarService(int id) {

		HotelariaModel deletarHotel = conversao.paraModel(acharIdService(id).get());
		HotelariaDto dtoDelete = new HotelariaDto();

		try {
			if (!(deletarHotel == null)) {
				BeanUtils.copyProperties(deletarHotel, dtoDelete);
				repository.delete(deletarHotel);
			} else {
				throw new NotFoundException("Hotel não encontrado.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException(e.getMessage());
		}

		return Optional.of(dtoDelete);
	}

	public boolean validarHotel(HotelariaDto hotel) {

		List<HotelariaModel> todosHoteis = repository.findAll();
		try {

			for (HotelariaModel hotelariaModel : todosHoteis) {
				if (hotelariaModel.getCnpj().equals(hotel.getCnpj())) {
					throw new ConflitoException("Hotel com CNPJ " + hotelariaModel.getCnpj() + " já existe!");
				}
			}

			if (hotel.getNome().isEmpty() || hotel.getCnpj().isEmpty())
				throw new ConflitoException("campo vazio, nome ou cnpj");

			if (hotel.getCnpj().length() != 14) {
				throw new ConflitoException("Erro: CNPJ deve conter 14 numeros");
			}

			if (hotel.getCapacidade() < 0) {

				throw new ConflitoException("Capacidade tem que ser maior que 0");

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConflitoException(e.getMessage());
		} 
		return true;
	}

	@Modifying
	@Transactional
	public HotelariaDto validarEdicaoHotel(int id, HotelariaDto hotel) {

		HotelariaModel hotelNovo = conversao.paraModel(acharIdService(id).get());

		try {

			if (!hotelNovo.getCnpj().equals(hotel.getCnpj()))
				throw new NotFoundException("CNPJ não pode ser alterado.");

			BeanUtils.copyProperties(hotel, hotelNovo, "id");
			repository.save(hotelNovo);

		} catch (NotFoundException e) {
			e.printStackTrace();
			throw new NotFoundException(e.getMessage());
		} catch (NegocioException e) {
			throw new NegocioException("Erro ao vaidar edição de hotel");
		}
		return conversao.paraDto(hotelNovo);
	}

}
