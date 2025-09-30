package com.project.dto.response;

import java.util.List;

public record GuDto(
        String guCode,
        String guName,
        List<DongDto> dongList
) {
}