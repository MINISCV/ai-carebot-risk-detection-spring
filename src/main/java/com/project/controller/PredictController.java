package com.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.service.PredictService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/predict")
@RequiredArgsConstructor
public class PredictController {
    private final PredictService predictService;
    
    @PostMapping("/sentiment")
    public ResponseEntity<String> handleCsvUpload(MultipartFile file) {
        if (file == null || file.isEmpty())
            return ResponseEntity.badRequest().body("업로드된 파일이 없습니다.");
        try {
            Long saveId = predictService.sendToFastApi(file);
            // 프론트에서 요구하는거 반환하는걸로
            return ResponseEntity.ok("저장완료 " + saveId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 처리 또는 FastAPI 통신 중 오류 발생: " + e.getMessage());
        }
    }
}