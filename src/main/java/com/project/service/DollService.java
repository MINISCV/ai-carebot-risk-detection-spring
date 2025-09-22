package com.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.Doll;
import com.project.dto.DollDto;
import com.project.persistence.DollRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DollService {
	private final DollRepository dollRepository;
	
	@Transactional
	public Doll createDoll(DollDto dollDto) {
		if (dollRepository.existsById(dollDto.id()))
			throw new IllegalArgumentException("이미 존재하는 인형 Id입니다.");

		Doll doll = Doll.builder()
				.id(dollDto.id())
				.build();
		
		return dollRepository.save(doll);
	}

    @Transactional(readOnly = true)
    public Optional<Doll> getDollById(String id) {
        return dollRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Doll> getAllDolls() {
        return dollRepository.findAll();
    }

    @Transactional
    public void deleteDoll(String id) {
        Doll doll = dollRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("인형을 찾을 수 없습니다: " + id));

        if (doll.getSenior() != null) {
            throw new IllegalStateException("해당 인형은 ID " + doll.getSenior().getId() + "번 시니어에게 할당되어 있어 삭제할 수 없습니다.");
        }
        
        dollRepository.delete(doll);
    }
}
