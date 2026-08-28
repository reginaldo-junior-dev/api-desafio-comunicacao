package reginaldo.api_desafio_comunicacao.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequest(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        @NotNull(message = "A data e hora são obrigatórias")
        @Future(message = "A data e hora deve ser futura")
        LocalDateTime dataHora,

        @NotBlank(message = "O destinatário é obrigatório")
        String destinatario,

        @NotBlank(message = "A mensagem é obrigatória")
        String mensagem
) {
}
