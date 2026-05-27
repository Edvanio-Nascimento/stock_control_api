package com.stock_control.api.exceptions.errors;

public record FieldErrorResponse(
        String field,
        String message
) {
}