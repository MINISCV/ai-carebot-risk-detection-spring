package com.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.AnalysisRequestDto;
import com.project.service.AnalyzeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/analyze")
@RequiredArgsConstructor
public class AnalyzeController {

    private final AnalyzeService analyzeService;

    @PostMapping
    public ResponseEntity<?> analyzeDialogues(MultipartFile file) {
        if (file == null || file.isEmpty())
            return ResponseEntity.badRequest().body("파일이 없거나 비어있습니다.");
        try {
        	AnalysisRequestDto response = analyzeService.analyzeAndSave(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("분석 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}