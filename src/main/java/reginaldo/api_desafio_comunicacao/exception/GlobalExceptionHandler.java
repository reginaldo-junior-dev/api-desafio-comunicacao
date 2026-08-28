package reginaldo.api_desafio_comunicacao.exception;

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
    public ResponseEntity<ErrorResponse> erro(MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            erros.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                422,
                "Unprocessable Entity",
                "Dados inválidos",
                       erros,
                  "/agendamento"
        );

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(errorResponse);
    }
}
