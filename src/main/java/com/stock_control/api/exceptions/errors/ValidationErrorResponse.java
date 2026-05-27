package com.stock_control.api.exceptions.errors;

import java.util.List;

public record ValidationErrorResponse(
        int status,
        String message,
        List<FieldErrorResponse> errors
) {
}