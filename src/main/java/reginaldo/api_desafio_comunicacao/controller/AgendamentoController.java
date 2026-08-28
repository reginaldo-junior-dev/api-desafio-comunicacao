package reginaldo.api_desafio_comunicacao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reginaldo.api_desafio_comunicacao.DTO.AgendamentoRequest;
import reginaldo.api_desafio_comunicacao.DTO.AgendamentoResponse;
import reginaldo.api_desafio_comunicacao.service.AgendamentoService;

import java.util.UUID;

@Tag(name = "agendamento", description = ("API para gerenciamento de agendamentos de comunicação"))
@RestController
@RequiredArgsConstructor
@RequestMapping("/agendamento")
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    @Operation(
            summary = "Cria um novo agendamento",
            description = "Agenda uma comunicação para ser enviada na data e hora informadas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados do agendamento inválidos")
    })

    @PostMapping
    public ResponseEntity<AgendamentoResponse> salvar (@Valid @RequestBody AgendamentoRequest request) {
       AgendamentoResponse resultado =  agendamentoService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @Operation(summary = "Consulta um agendamento",
               description = "Retorna os dados de um agendamento a partir do seu ID.")
    @ApiResponses({
                   @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
                   @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponse> consulta (@PathVariable UUID id) {
        AgendamentoResponse agendamentoResponse =  agendamentoService.consulta(id);
        return ResponseEntity.ok(agendamentoResponse);
    }

    @Operation(summary = "Cancela um agendamento",
              description = "Altera o status do agendamento para CANCELADO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "422", description = "Agendamento já está cancelado")
    })

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoResponse> cancelarAgendamento (@PathVariable UUID id) {
        AgendamentoResponse agendamentoResponse = agendamentoService.cancelarAgendamento(id);
        return ResponseEntity.ok(agendamentoResponse);
    }
}
