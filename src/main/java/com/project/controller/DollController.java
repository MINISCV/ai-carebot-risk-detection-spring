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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.dto.request.DollRequestDto;
import com.project.dto.response.DollResponseDto;
import com.project.service.DollService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dolls")
@RequiredArgsConstructor
public class DollController {
	private final DollService dollService;

    @PostMapping
    public ResponseEntity<DollResponseDto> createDoll(@Valid @RequestBody DollRequestDto requestDto) {
    	DollResponseDto dollDto = dollService.createDoll(requestDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dollDto.id())
				.toUri();
        return ResponseEntity.created(location).body(dollDto);
    }

    @GetMapping
    public ResponseEntity<List<DollResponseDto>> getAllDolls() {
        List<DollResponseDto> dolls = dollService.getAllDolls();
        return ResponseEntity.ok(dolls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DollResponseDto> getDollById(@PathVariable String id) {
        DollResponseDto doll = dollService.getDollById(id);
        return ResponseEntity.ok(doll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoll(@PathVariable String id) {
        dollService.deleteDoll(id);
        return ResponseEntity.noContent().build();
    }
}
