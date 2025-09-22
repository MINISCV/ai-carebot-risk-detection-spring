package com.project.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.domain.Doll;
import com.project.dto.DollDto;
import com.project.service.DollService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doll")
@RequiredArgsConstructor
public class DollController {
	private final DollService dollService;

    @PostMapping
    public ResponseEntity<Doll> createDoll(@RequestBody DollDto dollDto) {
    	Doll doll = dollService.createDoll(dollDto);
        URI location = URI.create("/api/doll/" + doll.getId());
        return ResponseEntity.created(location).body(doll);
    }

    @GetMapping
    public ResponseEntity<List<Doll>> getAllDolls() {
        List<Doll> dolls = dollService.getAllDolls();
        return ResponseEntity.ok(dolls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doll> getDollById(@PathVariable String id) {
        return dollService.getDollById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoll(@PathVariable String id) {
        dollService.deleteDoll(id);
        return ResponseEntity.noContent().build();
    }
}
