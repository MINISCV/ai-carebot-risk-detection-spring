package com.project.dto.response;

public record StateCountDto(
        long total,
        long positive,
        long danger,
        long critical,
        long emergency
) {
}