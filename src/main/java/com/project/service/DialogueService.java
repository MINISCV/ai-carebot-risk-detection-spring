package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.domain.Dialogue;
import com.project.persistence.DialogueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DialogueService {
	private final DialogueRepository dialogueRepository;
	
	public int saveDialogues(MultipartFile file) throws Exception {
        List<Dialogue> dialogues = Dialogue.parseSimpleCsv(file);
        dialogueRepository.saveAll(dialogues);
        return dialogues.size();
    }
}