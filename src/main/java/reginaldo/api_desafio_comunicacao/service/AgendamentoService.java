package reginaldo.api_desafio_comunicacao.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reginaldo.api_desafio_comunicacao.DTO.AgendamentoRequest;
import reginaldo.api_desafio_comunicacao.DTO.AgendamentoResponse;
import reginaldo.api_desafio_comunicacao.ENUM.StatusAgendamento;
import reginaldo.api_desafio_comunicacao.entity.Agendamento;
import reginaldo.api_desafio_comunicacao.exception.AgendamentoCancelado;
import reginaldo.api_desafio_comunicacao.exception.AgendamentoNaoEncontrado;
import reginaldo.api_desafio_comunicacao.mapper.AgendamentoMapper;
import reginaldo.api_desafio_comunicacao.repository.AgendamentoRepository;

import java.util.UUID;

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

    public AgendamentoResponse consulta (UUID id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new AgendamentoNaoEncontrado("Agendamento não encontrado: " + id));

        return agendamentoMapper.toResponse(agendamento);
    }

    public AgendamentoResponse cancelarAgendamento (UUID id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new AgendamentoNaoEncontrado("Agendamento não encontrado: " + id));

        if (agendamento.getStatusAgendamento() == StatusAgendamento.CANCELADO) {
            throw new AgendamentoCancelado("O agendamento já está cancelado: " + id);
        }

        agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);

        return agendamentoMapper.toResponse(agendamento);
    }
}
