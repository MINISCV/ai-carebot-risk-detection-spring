package com.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.domain.Dialogue;
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
    
    @GetMapping("/getAll/{dollId}")
    public ResponseEntity<?> getAllById(@PathVariable String dollId) {
        if (dollId == null)
            return ResponseEntity.badRequest().body("Id가 없습니다.");
        try {
            List<Dialogue> dialogues = dialogueService.findDialogueByDollId(dollId);
            if (dialogues.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dialogues);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다.");
        }
    }
}

