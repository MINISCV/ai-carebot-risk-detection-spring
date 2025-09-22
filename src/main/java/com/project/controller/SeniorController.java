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

import com.project.domain.Senior;
import com.project.dto.SeniorDto;
import com.project.service.SeniorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/senior")
@RequiredArgsConstructor
public class SeniorController {
    private final SeniorService seniorService;

    @PostMapping
    public ResponseEntity<Senior> createSenior(@RequestBody SeniorDto seniorDto) {
        Senior createdSenior = seniorService.createSenior(seniorDto);
        URI location = URI.create("/api/senior/" + createdSenior.getId());
        return ResponseEntity.created(location).body(createdSenior);
    }

    @GetMapping
    public ResponseEntity<List<Senior>> getAllSeniors() {
        List<Senior> seniors = seniorService.getAllSeniors();
        return ResponseEntity.ok(seniors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Senior> getSeniorById(@PathVariable Long id) {
        return seniorService.getSeniorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Senior> updateSenior(@PathVariable Long id, @RequestBody SeniorDto seniorDto) {
        Senior updatedSenior = seniorService.updateSenior(id, seniorDto);
        return ResponseEntity.ok(updatedSenior);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSenior(@PathVariable Long id) {
        seniorService.deleteSenior(id);
        return ResponseEntity.noContent().build();
    }
}