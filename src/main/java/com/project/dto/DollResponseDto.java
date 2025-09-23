package com.project.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.project.domain.Doll;

public record DollResponseDto(
        String id,
        SeniorResponseDto senior,
        List<OverallResultResponseDto> overallResults
) {
    public static DollResponseDto from(Doll doll) {
        SeniorResponseDto seniorDto = (doll.getSenior() != null)
                ? new SeniorResponseDto(doll.getSenior())
                : null;
        List<OverallResultResponseDto> overallResultDtos = doll.getOverallResults().stream()
                .map(OverallResultResponseDto::new)
                .collect(Collectors.toList());

        return new DollResponseDto(
            doll.getId(),
            seniorDto,
            overallResultDtos
        );
    }
}