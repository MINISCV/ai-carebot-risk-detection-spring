package com.project.domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Dialogue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String dollId;
	private String text;
	@Enumerated(EnumType.STRING)
	private Risk type;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime utteredAt;
	
    static public List<Dialogue> parseSimpleCsv(MultipartFile file) throws Exception {
    	final int FIELD_COUNT = 7;
        List<Dialogue> dialogues = new ArrayList<>();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm"); 
    	try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "EUC-KR"))) {
			br.readLine();

			String line;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(",", -1);
				if (fields.length < FIELD_COUNT) {
					System.err.println("경고: 필드 수가 부족한 라인 건너뜀: " + line);
					continue;
				}
				Dialogue dialogue = Dialogue.builder()
    			.dollId(fields[1].trim())
    			.text(fields[3].trim())
    			.type(Risk.valueOf(fields[2].trim().toUpperCase()))
    			.utteredAt(LocalDateTime.parse(fields[4].trim(), formatter)).build();
				dialogues.add(dialogue);
			}
		}
    	return dialogues;
    }    
}