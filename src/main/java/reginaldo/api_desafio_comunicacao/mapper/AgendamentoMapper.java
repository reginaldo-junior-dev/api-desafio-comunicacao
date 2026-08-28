package reginaldo.api_desafio_comunicacao.mapper;

import org.mapstruct.Mapper;
import reginaldo.api_desafio_comunicacao.DTO.AgendamentoRequest;
import reginaldo.api_desafio_comunicacao.DTO.AgendamentoResponse;
import reginaldo.api_desafio_comunicacao.entity.Agendamento;

@Mapper(componentModel = "spring")
public interface AgendamentoMapper {
    Agendamento toEntity(AgendamentoRequest request);
    AgendamentoResponse toResponse(Agendamento agendamento);
}
