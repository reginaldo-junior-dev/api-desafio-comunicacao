package reginaldo.api_desafio_comunicacao.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reginaldo.api_desafio_comunicacao.DTO.AgendamentoRequest;
import reginaldo.api_desafio_comunicacao.DTO.AgendamentoResponse;
import reginaldo.api_desafio_comunicacao.entity.Agendamento;
import reginaldo.api_desafio_comunicacao.mapper.AgendamentoMapper;
import reginaldo.api_desafio_comunicacao.repository.AgendamentoRepository;

@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;
    private final AgendamentoMapper agendamentoMapper;

    public AgendamentoResponse salvar (AgendamentoRequest request) {
        Agendamento agendamento = agendamentoMapper.toEntity(request);
        agendamentoRepository.save(agendamento);

        return agendamentoMapper.toResponse(agendamento);
    }
}
