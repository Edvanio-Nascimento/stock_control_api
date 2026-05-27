package com.stock_control.api.exceptions;

import com.stock_control_api.exceptions.errors.FieldErrorResponse;
import com.stock_control_api.exceptions.errors.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();

        error.put("timestamp: ", LocalDateTime.now());
        error.put("status: ", HttpStatus.NOT_FOUND.value());
        error.put("error: ", "Recurso não encontrado");
        error.put("message: ", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    String field = error.getField();
                    String message = error.getDefaultMessage();

                    // Se já existe erro "obrigatório", mantém ele
                    if (errors.containsKey(field)
                            && errors.get(field).contains("obrigatório")) {
                        return;
                    }

                    // Prioriza mensagem de obrigatório
                    if (message.contains("obrigatório")) {
                        errors.put(field, message);
                    } else {
                        errors.putIfAbsent(field, message);
                    }
                });

        List<FieldErrorResponse> fieldErrors = errors.entrySet()
                .stream()
                .map(entry -> new FieldErrorResponse(
                        entry.getKey(),
                        entry.getValue()
                ))
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(
                400,
                "Erro de validação",
                fieldErrors
        );

        return ResponseEntity.badRequest().body(response);
    }
}
