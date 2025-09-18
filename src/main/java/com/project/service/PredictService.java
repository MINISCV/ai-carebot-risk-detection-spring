package com.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.domain.AnalysisResult;
import com.project.domain.Basis;
import com.project.domain.Dialogue;
import com.project.domain.RecommendedAction;
import com.project.dto.AnalysisRequestDto;
import com.project.persistence.AnalysisResultRepository;
import com.project.persistence.DialogueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PredictService {
	private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
	private final AnalysisResultRepository analysisResultRepository;
	private final DialogueRepository dialogueRepository;
	private final String FASTAPI_URL = "http://localhost:8000/predict/sentiment";

	public Long sendToFastApi(MultipartFile file) throws Exception {
		List<Dialogue> dialogues = Dialogue.parseSimpleCsv(file);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<Dialogue>> entity = new HttpEntity<>(dialogues, headers);
		String response = restTemplate.postForObject(FASTAPI_URL, entity, String.class);
        AnalysisRequestDto analysisRequestDto = objectMapper.readValue(response, AnalysisRequestDto.class);
        Long savedId = saveAnalysis(analysisRequestDto);
		return savedId;
	}

	@Transactional
	public Long saveAnalysis(AnalysisRequestDto requestDto) {
		AnalysisResult analysisResult = new AnalysisResult();
		AnalysisRequestDto.AnalysisResultDto overallDto = requestDto.getAnalysisResult();
		analysisResult.setTimestamp(overallDto.getTimestamp());
		analysisResult.setRiskLevel(overallDto.getRiskLevel());

		Basis basis = new Basis();
		basis.setEvidence(overallDto.getBasis().getEvidence());
		basis.setSummary(overallDto.getBasis().getSummary());
		analysisResult.setBasis(basis);

		RecommendedAction action = new RecommendedAction();
		action.setCode(overallDto.getRecommendedAction().getCode());
		action.setDescription(overallDto.getRecommendedAction().getDescription());
		analysisResult.setRecommendedAction(action);

		Map<String, Double> overallScores = overallDto.getConfidenceScores();
		if (overallScores != null) {
			analysisResult.setPositiveScore(overallScores.get("positive"));
			analysisResult.setDangerScore(overallScores.get("danger"));
			analysisResult.setCriticalScore(overallScores.get("critical"));
			analysisResult.setEmergencyScore(overallScores.get("emergency"));
		}

		AnalysisResult savedAnalysisResult = analysisResultRepository.save(analysisResult);
		requestDto.getDialogues().forEach(dialogueDto -> {
			Dialogue dialogue = new Dialogue();
			dialogue.setDollId(dialogueDto.getDoll_id());
			dialogue.setText(dialogueDto.getText());
			dialogue.setUtteredAt(dialogueDto.getUttered_at());
			dialogue.setAnalysisResult(savedAnalysisResult);
			if (dialogueDto.getIndividualResult() != null) {
				dialogue.setIndividualRiskLevel(dialogueDto.getIndividualResult().getRiskLevel());
				System.out.println(dialogue.getIndividualRiskLevel().name());
				Map<String, Double> individualScores = dialogueDto.getIndividualResult().getConfidenceScores();
				if (individualScores != null) {
					dialogue.setIndividualPositiveScore(individualScores.get("positive"));
					dialogue.setIndividualDangerScore(individualScores.get("danger"));
					dialogue.setIndividualCriticalScore(individualScores.get("critical"));
					dialogue.setIndividualEmergencyScore(individualScores.get("emergency"));
				}
			}
			dialogueRepository.upsert(dialogue);
		});
		return savedAnalysisResult.getId();
	}
}
