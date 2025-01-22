package com.agencia.backend.presentation.dto.error;

import java.util.Set;

public record ValidationErrorsResponse(String message, int statusCode, Set<ValidationError> errors) {
}
