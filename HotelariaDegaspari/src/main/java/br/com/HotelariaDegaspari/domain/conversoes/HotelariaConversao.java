package br.com.HotelariaDegaspari.domain.conversoes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.HotelariaDegaspari.api.dto.HotelariaDto;
import br.com.HotelariaDegaspari.infrastructure.model.HotelariaModel;

@Component
public class HotelariaConversao {
	
	public HotelariaDto paraDto(HotelariaModel model) {
		HotelariaDto dto = new HotelariaDto();
		BeanUtils.copyProperties(model, dto);
		return dto;
	}
	
	public HotelariaModel paraModel(HotelariaDto dto) {
		HotelariaModel model = new HotelariaModel();
		BeanUtils.copyProperties(dto, model);
		return model;
	}

	public List<HotelariaDto> listaParaDto(List<HotelariaModel> listaModel) {

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

}
