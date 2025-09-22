package com.project.dto;

import java.util.List;

public record ReasonDto(List<String> evidence, String summary) {
}