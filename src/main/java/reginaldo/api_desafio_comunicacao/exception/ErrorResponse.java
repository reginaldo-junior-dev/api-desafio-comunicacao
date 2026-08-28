package reginaldo.api_desafio_comunicacao.exception;

import java.util.Map;

public record ErrorResponse (
        int status,
        String error,
        String message,
        Map<String, String> erros,
        String path
) {

}
