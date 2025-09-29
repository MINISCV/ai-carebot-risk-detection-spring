package com.project.controller;

import com.project.dto.response.DashboardResponseDto;
import com.project.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponseDto> getDashboardData() {
        DashboardResponseDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
}