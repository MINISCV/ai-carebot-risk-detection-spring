package com.project.dto.response;

import org.springframework.data.domain.Page;
import java.util.List;

public record CustomPageDto<T>(
    List<T> content,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages,
    boolean isLast,
    boolean isFirst
) {
    public static <T> CustomPageDto<T> from(Page<T> page) {
        return new CustomPageDto<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isLast(),
            page.isFirst()
        );
    }
}