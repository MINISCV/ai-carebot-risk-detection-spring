package com.project.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.dto.request.OverallResultSearchCondition;
import com.project.dto.response.AnalysisDetailResponseDto;
import com.project.dto.response.AnalysisResponseWithIdDto;
import com.project.dto.response.CustomPageDto;
import com.project.dto.response.OverallResultListResponseDto;
import com.project.service.AnalyzeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/analyze")
@RequiredArgsConstructor
public class AnalyzeController {
    private final AnalyzeService analyzeService;

    @PostMapping
    public ResponseEntity<AnalysisResponseWithIdDto> analyzeDialogues(MultipartFile file) {
    	AnalysisResponseWithIdDto response = analyzeService.analyzeAndSave(file);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(response.id())
				.toUri();
        return ResponseEntity.created(location).body(response);
    }
    
    @GetMapping
    public ResponseEntity<CustomPageDto<OverallResultListResponseDto>> searchOverallResults(
            @ModelAttribute OverallResultSearchCondition condition, Pageable pageable) {
        Page<OverallResultListResponseDto> resultsPage = analyzeService.searchOverallResults(condition, pageable);
        return ResponseEntity.ok(CustomPageDto.from(resultsPage));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AnalysisDetailResponseDto> getAnalysisDetails(@PathVariable Long id) {
        AnalysisDetailResponseDto analysisDetails = analyzeService.getAnalysisDetails(id);
        return ResponseEntity.ok(analysisDetails);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
    	analyzeService.deleteAnalysis(id);
        return ResponseEntity.noContent().build();
    }
}