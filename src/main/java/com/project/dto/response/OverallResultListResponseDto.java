package com.project.dto.response;

import com.project.domain.analysis.Risk;
import com.project.domain.senior.Sex;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public record OverallResultListResponseDto(
        Long overallResultId,
        Risk label,
        String summary,
        LocalDateTime timestamp,
        String dollId,
        Long seniorId,
        String name,
        int age,
        Sex sex,
        String gu,
        String dong
) {
    @QueryProjection
    public OverallResultListResponseDto(Long overallResultId, Risk label, String summary, LocalDateTime timestamp, String dollId, Long seniorId, String name, LocalDate birthDate, Sex sex, String gu, String dong) {
        this(
            overallResultId,
            label,
            summary,
            timestamp,
            dollId,
            seniorId,
            name,
            Period.between(birthDate, LocalDate.now()).getYears(),
            sex,
            gu,
            dong
        );
    }
}