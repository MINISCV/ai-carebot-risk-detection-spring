package com.project.service;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.project.domain.ConfidenceScores;
import com.project.domain.Dialogue;
import com.project.domain.Doll;
import com.project.domain.OverallResult;
import com.project.domain.Reason;
import com.project.domain.Risk;
import com.project.dto.AnalysisRequestDto;
import com.project.dto.ConfidenceScoresDto;
import com.project.dto.DialogueDto;
import com.project.dto.DialogueForApiRequest;
import com.project.persistence.DollRepository;
import com.project.persistence.OverallResultRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyzeService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final DollRepository dollRepository;
    private final OverallResultRepository overallResultRepository;

    @Value("${python.server.url}")
    private String pythonServerUrl;

    @Transactional
    public AnalysisRequestDto analyzeAndSave(MultipartFile file) throws Exception {
        List<DialogueForApiRequest> pythonRequestList = new ArrayList<>();

        DateTimeFormatter csvFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss");
        DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            reader.readNext();
            String[] columns;
            while ((columns = reader.readNext()) != null) {
                String dollId = columns[0].trim();
                String text = columns[1].trim();
                String utteredAtCsv = columns[2].trim();
                LocalDateTime dateTime = LocalDateTime.parse(utteredAtCsv, csvFormatter);
                String utteredAtForApi = dateTime.format(isoFormatter);
                if (dollId.isEmpty() || text.isEmpty() || utteredAtCsv.isEmpty()) {
                    log.warn("Skipping empty or incomplete line in CSV: doll_id={}, text={}, uttered_at={}", dollId, text, utteredAtCsv);
                    continue;
                }
                pythonRequestList.add(new DialogueForApiRequest(dollId, text, utteredAtForApi));
            }
        } catch (CsvValidationException e) {
            throw new IllegalArgumentException("잘못된 형식의 CSV 파일입니다.", e);
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<DialogueForApiRequest>> requestEntity = new HttpEntity<>(pythonRequestList, headers);
        log.info("Sending analysis request to Python server");
        AnalysisRequestDto apiResponse = restTemplate.postForObject(
                pythonServerUrl + "/analyze",
                requestEntity,
                AnalysisRequestDto.class);

        log.info("Received response from Python server:");
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiResponse));

        saveAnalysisResult(apiResponse);
        
        return apiResponse;
    }
    
    private void saveAnalysisResult(AnalysisRequestDto responseDto) {
        String responseDollId = responseDto.overallResult().dollId();
        
        Doll doll = dollRepository.findById(responseDollId)
                .orElseThrow(() -> new EntityNotFoundException("Doll not found with id: " + responseDollId));

        ConfidenceScoresDto overallScoresDto = responseDto.overallResult().confidenceScores();
        ConfidenceScores overallScores = ConfidenceScores.builder()
                .positive(Double.parseDouble(overallScoresDto.positive()))
                .danger(Double.parseDouble(overallScoresDto.danger()))
                .critical(Double.parseDouble(overallScoresDto.critical()))
                .emergency(Double.parseDouble(overallScoresDto.emergency()))
                .build();
        List<String> evidenceTexts = responseDto.overallResult().reason().evidence().stream()
                .map(evidenceDto -> evidenceDto.text())
                .collect(Collectors.toList());

        Reason reason = Reason.builder()
                .reasons(evidenceTexts)
                .summary(responseDto.overallResult().reason().summary())
                .build();

        OverallResult overallResult = OverallResult.builder()
                .doll(doll)
                .label(Risk.valueOf(responseDto.overallResult().label().toUpperCase()))
                .confidenceScores(overallScores)
                .reason(reason)
                .build();

        for (DialogueDto dialogueDto : responseDto.dialogueResult()) {
            ConfidenceScoresDto dialogueScoresDto = dialogueDto.confidenceScores();
            ConfidenceScores dialogueScores = ConfidenceScores.builder()
                    .positive(Double.parseDouble(dialogueScoresDto.positive()))
                    .danger(Double.parseDouble(dialogueScoresDto.danger()))
                    .critical(Double.parseDouble(dialogueScoresDto.critical()))
                    .emergency(Double.parseDouble(dialogueScoresDto.emergency()))
                    .build();
            
            Dialogue dialogue = Dialogue.builder()
                    .text(dialogueDto.text())
                    .utteredAt(dialogueDto.utteredAt())
                    .label(Risk.valueOf(dialogueDto.label().toUpperCase()))
                    .confidenceScores(dialogueScores)
                    .build();
            
            overallResult.addDialogue(dialogue);
        }

        overallResultRepository.save(overallResult);
        log.info("Successfully saved analysis result for doll_id: {}", responseDollId);
    }
}