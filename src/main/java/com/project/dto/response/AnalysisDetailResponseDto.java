package com.project.dto.response;

import com.project.domain.analysis.OverallResult;
import com.project.domain.analysis.Risk;
import com.project.dto.ConfidenceScoresDto;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public record AnalysisDetailResponseDto(
        String seniorName,
        String diseases,
        int age,

        String dollId,
        Risk label,
        ConfidenceScoresDto confidenceScores,
        List<String> reasons,
        String summary,
        String treatmentPlan,

        List<DialogueDetailDto> dialogues
) {
    public static AnalysisDetailResponseDto from(OverallResult overallResult) {
        ConfidenceScoresDto scoresDto = new ConfidenceScoresDto(
                overallResult.getConfidenceScores().getPositive(),
                overallResult.getConfidenceScores().getDanger(),
                overallResult.getConfidenceScores().getCritical(),
                overallResult.getConfidenceScores().getEmergency()
        );

        List<DialogueDetailDto> dialogueDtos = overallResult.getDialogues().stream()
                .map(DialogueDetailDto::from)
                .collect(Collectors.toList());

        return new AnalysisDetailResponseDto(
                overallResult.getSenior().getName(),
                overallResult.getSenior().getMedicalInfo().getDiseases(),
                Period.between(overallResult.getSenior().getBirthDate(), LocalDate.now()).getYears(),
                overallResult.getDoll().getId(),
                overallResult.getLabel(),
                scoresDto,
                overallResult.getReason().getReasons(),
                overallResult.getReason().getSummary(),
                overallResult.getTreatmentPlan(),
                dialogueDtos
        );
    }
}