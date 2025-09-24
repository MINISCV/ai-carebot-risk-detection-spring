package com.project.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping
    public ResponseEntity<SeniorResponseDto> createSenior(@RequestBody SeniorRequestDto requestDto) {
    	SeniorResponseDto senior = seniorService.createSenior(requestDto);
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

    @PutMapping("/{id}")
    public ResponseEntity<SeniorResponseDto> updateSenior(@PathVariable Long id, @RequestBody SeniorRequestDto seniorDto) {
    	SeniorResponseDto senior = seniorService.updateSenior(id, seniorDto);
        return ResponseEntity.ok(senior);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSenior(@PathVariable Long id) {
        seniorService.deleteSenior(id);
        return ResponseEntity.noContent().build();
    }
}