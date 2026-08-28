package reginaldo.api_desafio_comunicacao.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> erroValidacao
            (MethodArgumentNotValidException ex, HttpServletRequest request) {

            Map<String, String> erros = new HashMap<>();

            for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                erros.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            ErrorResponse errorResponse = new ErrorResponse(
                             HttpStatus.UNPROCESSABLE_CONTENT.value(),
                       "Unprocessable Entity",
                    "Dados inválidos",
                             erros,
                             request.getRequestURI()
            );

            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_CONTENT)
                    .body(errorResponse);
    }

    @ExceptionHandler(AgendamentoNaoEncontrado.class)
    public ResponseEntity<ErrorResponse> erroConsulta
            (AgendamentoNaoEncontrado ex, HttpServletRequest request) {

            ErrorResponse errorResponse = new ErrorResponse(
                      HttpStatus.NOT_FOUND.value(),
                "Not Found",
                      ex.getMessage(),
                null,
                      request.getRequestURI()

        );

             return ResponseEntity
                     .status(HttpStatus.NOT_FOUND)
                     .body(errorResponse);
    }

    @ExceptionHandler(AgendamentoCancelado.class)
    public ResponseEntity<ErrorResponse> erroAgendamentoCancelado
            (AgendamentoCancelado ex, HttpServletRequest request) {

            ErrorResponse errorResponse = new ErrorResponse(
                      HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Unprocessable Entity",
                      ex.getMessage(),
                null,
                      request.getRequestURI()
        );
            return ResponseEntity
                    .status
                    (HttpStatus.UNPROCESSABLE_CONTENT)
                    .body(errorResponse);
    }
}
