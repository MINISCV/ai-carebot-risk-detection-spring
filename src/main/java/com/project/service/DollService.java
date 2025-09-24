package com.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.senior.Doll;
import com.project.dto.request.DollRequestDto;
import com.project.dto.response.DollResponseDto;
import com.project.persistence.DollRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DollService {
	private final DollRepository dollRepository;
	
	@Transactional
	public DollResponseDto createDoll(DollRequestDto requestDto) {
		if (dollRepository.existsById(requestDto.id()))
			throw new IllegalArgumentException("이미 존재하는 인형 Id입니다.");

		Doll doll = dtoToDoll(requestDto);
		
		dollRepository.save(doll);
		return DollResponseDto.from(doll);
	}

    @Transactional(readOnly = true)
    public DollResponseDto getDollById(String id) {
        Doll doll = dollRepository.findByIdWithSenior(id)
                .orElseThrow(() -> new EntityNotFoundException("인형을 찾을 수 없습니다: " + id));
        return DollResponseDto.from(doll);
    }

    @Transactional(readOnly = true)
    public List<DollResponseDto> getAllDolls() {
        return dollRepository.findAllWithSenior().stream()
                .map(DollResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDoll(String id) {
        Doll doll = dollRepository.findByIdWithSenior(id)
        		.orElseThrow(() -> new EntityNotFoundException("인형을 찾을 수 없습니다: " + id));

        if (doll.getSenior() != null) {
            throw new IllegalStateException("해당 인형은 ID " + doll.getSenior().getId() + "번 시니어에게 할당되어 있어 삭제할 수 없습니다.");
        }
        
        dollRepository.delete(doll);
    }
    
    private Doll dtoToDoll(DollRequestDto dto) {
        if (dto == null)
            return null;
        return Doll.builder()
                .id(dto.id())
                .build();
    }
}
