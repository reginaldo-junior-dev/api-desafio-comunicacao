package reginaldo.api_desafio_comunicacao.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reginaldo.api_desafio_comunicacao.DTO.AgendamentoRequest;
import reginaldo.api_desafio_comunicacao.DTO.AgendamentoResponse;
import reginaldo.api_desafio_comunicacao.service.AgendamentoService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agendamento")
public class AgendamentoController {
    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponse> salvar (@Valid @RequestBody AgendamentoRequest request) {
       AgendamentoResponse resultado =  agendamentoService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponse> consulta (@PathVariable UUID id) {
        AgendamentoResponse agendamentoResponse =  agendamentoService.consulta(id);
        return ResponseEntity.ok(agendamentoResponse);
    }
}
