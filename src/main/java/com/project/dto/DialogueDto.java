package com.project.dto;

import java.time.LocalDateTime;

public record DialogueDto(String text, LocalDateTime utteredAt, String label, ConfidenceScoresDto confidenceScores) {
}