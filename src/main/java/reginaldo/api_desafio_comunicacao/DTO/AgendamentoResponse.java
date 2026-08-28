package reginaldo.api_desafio_comunicacao.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import reginaldo.api_desafio_comunicacao.ENUM.StatusAgendamento;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgendamentoResponse(
        UUID id,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataHora,
        String destinatario,
        String mensagem,
        StatusAgendamento statusAgendamento
) {
}
