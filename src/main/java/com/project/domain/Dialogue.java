package com.project.domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(uniqueConstraints = {
	    @UniqueConstraint(
	        name = "doll_uttered_at_unique",
	        columnNames = {"dollId", "utteredAt"}
	    )
	})
public class Dialogue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String dollId;
	@Column(length = 2000)
	private String text;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime utteredAt;
	
    static public List<Dialogue> parseSimpleCsv(MultipartFile file) throws Exception {
    	final int FIELD_COUNT = 3;
        List<Dialogue> dialogues = new ArrayList<>();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss");
        Pattern pattern = Pattern.compile("(?<=\")[^\"]*(?=\"(,|$))|[^,]+");
    	try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {}

                List<String> fields = new ArrayList<>();
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String field = matcher.group();
                    if (field.startsWith("\"") && field.endsWith("\"")) {
                        field = field.substring(1, field.length() - 1);
                    }
                    fields.add(field.trim());
                }
                if (fields.size() < FIELD_COUNT) {
                    System.err.println("경고: 필드 수가 부족한 라인 건너뜀: " + line);
                    continue;
                }
                String dollId = fields.get(0).trim();
                String utteredAtStr = fields.get(fields.size() - 1).trim();
                String text = "";
                if (fields.size() > 2) {
                    StringBuilder textBuilder = new StringBuilder();
                    for (int i = 1; i < fields.size() - 1; i++) {
                        textBuilder.append(fields.get(i).trim());
                        if (i < fields.size() - 2) {
                            textBuilder.append(", ");
                        }
                    }
                    text = textBuilder.toString();
                }
                try {
                    LocalDateTime utteredAt = LocalDateTime.parse(utteredAtStr, formatter);
                    Dialogue dialogue = Dialogue.builder()
                        .dollId(dollId)
                        .text(text)
                        .utteredAt(utteredAt)
                        .build();
                    dialogues.add(dialogue);
                } catch (DateTimeParseException e) {
                    System.err.println("경고: 날짜 파싱 오류 발생. 라인 건너뜀: " + line + " (오류: " + e.getMessage() + ")");
                    continue;
                }
			}
		}
    	return dialogues;
    }    
}
