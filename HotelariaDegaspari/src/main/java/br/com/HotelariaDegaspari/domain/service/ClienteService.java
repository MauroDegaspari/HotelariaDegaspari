package br.com.HotelariaDegaspari.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.HotelariaDegaspari.api.dto.ClienteDto;
import br.com.HotelariaDegaspari.domain.conversoes.ClienteConversao;
import br.com.HotelariaDegaspari.infrastructure.model.ClienteModel;
import br.com.HotelariaDegaspari.infrastructure.repository.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository repo;
	private final ClienteConversao conversao;

	@Autowired
	public ClienteService(ClienteRepository repo, ClienteConversao conversao) {
		this.repo = repo;
		this.conversao = conversao;
	}

	public ClienteDto salvarCliente(ClienteDto cliente) {
		ClienteModel clienteModel = conversao.paraModel(cliente);

		repo.save(clienteModel);
		
		return conversao.paraDto(clienteModel);
	}

}
