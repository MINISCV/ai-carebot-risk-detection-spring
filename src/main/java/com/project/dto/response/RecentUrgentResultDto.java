package com.project.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import com.project.domain.analysis.Risk;
import com.project.domain.senior.Gu;
import com.project.domain.senior.Haengjeongdong;
import com.project.domain.senior.Sex;
import com.querydsl.core.annotations.QueryProjection;

public record RecentUrgentResultDto(
        Long overallResultId,
        Risk label,
        String seniorName,
        int age,
        Sex sex,
        String gu,
        String dong,
        String summary,
        LocalDateTime timestamp
) {
    @QueryProjection
    public RecentUrgentResultDto(Long overallResultId, Risk label, String seniorName, LocalDate birthDate, Sex sex, Gu gu, Haengjeongdong dong, String summary, LocalDateTime timestamp) {
        this(
            overallResultId,
            label,
            seniorName,
            Period.between(birthDate, LocalDate.now()).getYears(),
            sex,
            gu.getKoreanName(),
            dong.getKoreanName(),
            summary,
            timestamp
        );
    }
}