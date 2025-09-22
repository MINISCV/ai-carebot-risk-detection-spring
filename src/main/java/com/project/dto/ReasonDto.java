package com.project.dto;

import java.util.List;

public record ReasonDto(List<EvidenceDto> evidence, String summary) {
}