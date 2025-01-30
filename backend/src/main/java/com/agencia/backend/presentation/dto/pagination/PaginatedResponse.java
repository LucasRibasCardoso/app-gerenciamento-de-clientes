package com.agencia.backend.presentation.dto.pagination;

import java.util.List;

public record PaginatedResponse<T>(
    List<T> data,
    int totalPages,
    long totalElements
) {}
