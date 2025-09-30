package com.project.controller;

import com.project.dto.response.GuDto;
import com.project.service.AdministrativeDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/administrative-districts")
@RequiredArgsConstructor
public class AdministrativeDistrictController {
    private final AdministrativeDistrictService administrativeDistrictService;

    @GetMapping
    public ResponseEntity<List<GuDto>> getDistricts() {
        List<GuDto> districts = administrativeDistrictService.getAdministrativeDistricts();
        return ResponseEntity.ok(districts);
    }
}