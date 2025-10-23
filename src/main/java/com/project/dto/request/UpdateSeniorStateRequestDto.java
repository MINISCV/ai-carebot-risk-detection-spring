package com.project.dto.request;

import com.project.domain.analysis.Risk;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSeniorStateRequestDto(
        @NotNull(message = "변경할 상태는 필수입니다.")
        Risk newState,

        @NotBlank(message = "상태 변경 사유는 필수입니다.")
        String reason
) {
}