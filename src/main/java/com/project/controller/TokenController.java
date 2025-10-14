package com.project.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
        String newRefreshToken = JWTUtil.getRefreshJWT(username);
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 60 * 24 * 7)
                .sameSite("None")
                .secure(true)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, newAccessToken)
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(null);
    }
}