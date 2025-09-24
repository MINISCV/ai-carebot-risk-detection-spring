package com.project.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.util.JWTUtil;

@RestController
public class TokenController {
    @PostMapping("/api/refresh")
    public ResponseEntity<String> refreshAccessToken(@CookieValue(value = "refresh_token", required = false) String refreshToken) {
        if (refreshToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 없음");
        String username = JWTUtil.getRefreshClaim(refreshToken);
        String newAccessToken = JWTUtil.getJWT(username);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, newAccessToken);
        return ResponseEntity.ok().headers(headers).body("Access Token 갱신됨.");
    }
}