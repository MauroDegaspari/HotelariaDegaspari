package br.com.HotelariaDegaspari.domain.conversoes;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.HotelariaDegaspari.api.dto.ClienteDto;
import br.com.HotelariaDegaspari.infrastructure.model.ClienteModel;

@Component
public class ClienteConversao {

	public ClienteDto paraDto(ClienteModel cliente){
		ClienteDto clienteDto = new ClienteDto();
		BeanUtils.copyProperties(cliente, clienteDto);
		return clienteDto;
	}
	
	public ClienteModel paraModel(ClienteDto cliente){
		ClienteModel clienteModel = new ClienteModel();
		BeanUtils.copyProperties(cliente, clienteModel);
		return clienteModel;
	}
}
