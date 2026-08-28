package reginaldo.api_desafio_comunicacao.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequest(
        @Schema(
                description = "Data e hora programadas para o envio",
                example = "30/08/2026 18:00:00"
        )
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        @NotNull(message = "A data e hora são obrigatórias")
        @Future(message = "A data e hora deve ser futura")
        LocalDateTime dataHora,

        @Schema(
                description = "Destinatário da comunicação",
                example = "cliente@email.com"
        )
        @NotBlank(message = "O destinatário é obrigatório")
        String destinatario,

        @Schema(
                description = "Mensagem que será enviada",
                example = "Sua comunicação foi agendada com sucesso."
        )
        @NotBlank(message = "A mensagem é obrigatória")
        String mensagem
) {
}
