package com.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.response.AnalysisResponseDto;
import com.project.dto.response.OverallResultResponseDto;
import com.project.service.AnalyzeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/analyze")
@RequiredArgsConstructor
public class AnalyzeController {
    private final AnalyzeService analyzeService;

    @PostMapping
    public ResponseEntity<AnalysisResponseDto> analyzeDialogues(MultipartFile file) {
    	AnalysisResponseDto response = analyzeService.analyzeAndSave(file);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<OverallResultResponseDto>> getAnalysisAll() {
    	List<OverallResultResponseDto> response = analyzeService.getAll();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OverallResultResponseDto> getAnalysisById(@PathVariable Long id) {
        OverallResultResponseDto responseDto = analyzeService.getById(id);
        return ResponseEntity.ok(responseDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
    	analyzeService.deleteAnalysis(id);
        return ResponseEntity.noContent().build();
    }
}