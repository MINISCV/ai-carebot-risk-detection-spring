package com.project.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import com.project.domain.analysis.Risk;
import com.project.domain.senior.Gu;
import com.project.domain.senior.Haengjeongdong;
import com.project.domain.senior.Sex;
import com.querydsl.core.annotations.QueryProjection;

public record SeniorListResponseDto(
        Long seniorId,
        String name,
        int age,
        Sex sex,
        String gu,
        String dong,
        Risk state,
        String dollId,
        String phone,
        LocalDateTime createdAt
) {
    @QueryProjection
    public SeniorListResponseDto(Long seniorId, String name, LocalDate birthDate, Sex sex, Gu gu, Haengjeongdong dong, Risk state, String dollId, String phone, LocalDateTime createdAt) {
        this(
            seniorId,
            name,
            Period.between(birthDate, LocalDate.now()).getYears(),
            sex,
            gu.getKoreanName(),
            dong.getKoreanName(),
            state,
            dollId,
            phone,
            createdAt
        );
    }
}