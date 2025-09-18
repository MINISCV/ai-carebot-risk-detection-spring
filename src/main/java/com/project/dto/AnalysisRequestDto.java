package com.project.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.project.domain.Risk;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisRequestDto {
    private AnalysisResultDto analysisResult;
    private List<DialogueDto> dialogues;

    @Getter
    @Setter
    public static class AnalysisResultDto {
        private LocalDateTime timestamp;
        private Risk riskLevel;
        private Map<String, Double> confidenceScores;
        private BasisDto basis;
        private RecommendedActionDto recommendedAction;
    }

    @Getter
    @Setter
    public static class DialogueDto {
        private String doll_id;
        private String text;
        private LocalDateTime uttered_at;
        private IndividualResultDto individualResult;
    }

    @Getter
    @Setter
    public static class IndividualResultDto {
        private Risk riskLevel;
        private Map<String, Double> confidenceScores;
    }

    @Getter
    @Setter
    public static class BasisDto {
        private List<String> evidence;
        private String summary;
    }

    @Getter
    @Setter
    public static class RecommendedActionDto {
        private String code;
        private String description;
    }
}