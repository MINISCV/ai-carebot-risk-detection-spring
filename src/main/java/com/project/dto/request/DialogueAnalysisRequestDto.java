package com.project.dto.request;

import java.time.LocalDateTime;

public record DialogueAnalysisRequestDto(
		String dollId,
		String text,
		LocalDateTime utteredAt
) {
}