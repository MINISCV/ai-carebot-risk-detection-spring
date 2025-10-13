package com.project.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.exception.PythonApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class PythonApiErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
            String responseBody = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
            String errorMessage = parseErrorMessage(responseBody);
            
            throw new PythonApiException(errorMessage, HttpStatus.SERVICE_UNAVAILABLE);
        }

        throw new PythonApiException(
                "분석 서버에서 에러가 발생했습니다. Status: " + response.getStatusCode(),
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    private String parseErrorMessage(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);

            if (rootNode.has("validation_msg")) {
                return rootNode.get("validation_msg").asText();
            }

            if (rootNode.has("detail") && rootNode.get("detail").isArray()) {
                JsonNode detailNode = rootNode.get("detail").get(0);
                if (detailNode != null && detailNode.has("msg")) {
                    String fieldPath = detailNode.get("loc").toString();
                    String message = detailNode.get("msg").asText();
                    return String.format("분석 서버 유효성 검사 실패: %s (필드: %s)", message, fieldPath);
                }
            }
        } catch (IOException e) {
            return "분석 서버 에러 메시지를 파싱할 수 없습니다: " + responseBody;
        }
        return "분석 서버에서 처리할 수 없는 요청입니다.";
    }
}