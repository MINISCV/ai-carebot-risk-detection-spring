package com.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.service.DialogueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dialogue")
@RequiredArgsConstructor
public class DialogueController {
    private final DialogueService dialogueService;

    @PostMapping(value = "/upload", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCsv(MultipartFile file) {
        if (file == null || file.isEmpty())
            return ResponseEntity.badRequest().body("업로드된 파일이 없습니다.");
        try {
            int rows = dialogueService.saveDialogues(file);
            return ResponseEntity.ok(rows + "개 업로드 되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
}

