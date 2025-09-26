package com.project.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.dto.request.SeniorRequestDto;
import com.project.dto.response.SeniorResponseDto;
import com.project.service.SeniorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seniors")
@RequiredArgsConstructor
public class SeniorController {
    private final SeniorService seniorService;
    @Value("${senior.photo.upload-path}")
    private String uploadPath;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SeniorResponseDto> createSenior(
            @RequestPart("senior") SeniorRequestDto requestDto,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
    	SeniorResponseDto senior = seniorService.createSenior(requestDto, photo);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(senior.id())
				.toUri();
        return ResponseEntity.created(location).body(senior);
    }

    @GetMapping
    public ResponseEntity<List<SeniorResponseDto>> getAllSeniors() {
        List<SeniorResponseDto> seniors = seniorService.getAllSeniors();
        return ResponseEntity.ok(seniors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeniorResponseDto> getSeniorById(@PathVariable Long id) {
    	SeniorResponseDto senior = seniorService.getSeniorById(id);
    	return ResponseEntity.ok(senior);
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SeniorResponseDto> updateSenior(
            @PathVariable Long id, 
            @RequestPart("senior") SeniorRequestDto seniorDto,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
    	SeniorResponseDto senior = seniorService.updateSenior(id, seniorDto, photo);
        return ResponseEntity.ok(senior);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSenior(@PathVariable Long id) {
        seniorService.deleteSenior(id);
        return ResponseEntity.noContent().build();
    }
}