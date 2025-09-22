package com.project.dto;

import java.time.LocalDateTime;

public record DialogueDto(Long seq, String dollId, String text, LocalDateTime utteredAt, String label, ConfidenceScoresDto confidenceScores) {
}