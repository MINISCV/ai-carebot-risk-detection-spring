package com.project.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.project.domain.Dialogue;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PredictService {
	private final RestTemplate restTemplate;
	private final String FASTAPI_URL = "http://localhost:8000/predict/sentiment";

	public String sendToFastApi(MultipartFile file) throws Exception {
		List<Dialogue> dialogues = Dialogue.parseSimpleCsv(file);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<Dialogue>> entity = new HttpEntity<>(dialogues, headers);
		String response = restTemplate.postForObject(FASTAPI_URL, entity, String.class);
		return response;
	}
}
