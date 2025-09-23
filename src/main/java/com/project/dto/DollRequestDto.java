package com.project.dto;

import jakarta.validation.constraints.NotBlank;

public record DollRequestDto(@NotBlank(message = "인형 ID는 필수입니다.") String id) {
}
