package com.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.senior.Doll;
import com.project.domain.senior.Guardian;
import com.project.domain.senior.MedicalInfo;
import com.project.domain.senior.Senior;
import com.project.dto.request.SeniorRequestDto;
import com.project.dto.response.SeniorResponseDto;
import com.project.persistence.DollRepository;
import com.project.persistence.SeniorRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeniorService {
	private final SeniorRepository seniorRepository;
	private final DollRepository dollRepository;

	@Transactional
	public SeniorResponseDto createSenior(SeniorRequestDto requestDto) {
		Doll doll = dollRepository.findByIdWithSenior(requestDto.dollId())
				.orElseThrow(() -> new EntityNotFoundException("인형 " + requestDto.dollId() + " 없음."));
		
		if (doll.getSenior() != null)
			throw new IllegalArgumentException("해당 인형은 이미 사용 중.");

		Senior senior = dtoToSenior(requestDto);
		senior.changeDoll(doll);
		seniorRepository.save(senior);
		
		return new SeniorResponseDto(senior);
	}

	@Transactional(readOnly = true)
	public SeniorResponseDto getSeniorById(Long id) {
		Senior senior = seniorRepository.findByIdWithDoll(id)
				.orElseThrow(() -> new EntityNotFoundException("시니어 " + id + "는 없음."));
		return new SeniorResponseDto(senior);
	}

	@Transactional(readOnly = true)
	public List<SeniorResponseDto> getAllSeniors() {
		return seniorRepository.findAllWithDoll().stream()
				.map(SeniorResponseDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public SeniorResponseDto updateSenior(Long id, SeniorRequestDto seniorDto) {
		Senior existingSenior = seniorRepository.findByIdWithDoll(id)
				.orElseThrow(() -> new EntityNotFoundException("시니어 " + id + "는 없음."));

		if (!existingSenior.getDoll().getId().equals(seniorDto.dollId())) {
			Doll newDoll = dollRepository.findById(seniorDto.dollId()).orElseThrow(
					() -> new EntityNotFoundException("인형 " + seniorDto.dollId() + "없음."));
			if (newDoll.getSenior() != null)
				throw new IllegalArgumentException("해당 인형은 이미 사용 중.");
			existingSenior.changeDoll(newDoll);
		}

		updateSenior(existingSenior, seniorDto);
		 
		return new SeniorResponseDto(existingSenior);
	}

	@Transactional
	public void deleteSenior(Long id) {
		Senior senior = seniorRepository.findByIdWithDoll(id)
				.orElseThrow(() -> new EntityNotFoundException("시니어 " + id + "는 없음."));
		if (senior.getDoll() != null)
	        senior.changeDoll(null);
		seniorRepository.deleteById(id);
	}

    private MedicalInfo dtoToMedicalInfo(SeniorRequestDto dto) {
        return MedicalInfo.builder()
        		.diseases(dto.diseases())
        		.medications(dto.medications())
        		.build();
    }
	
    private Guardian dtoToGuardian(SeniorRequestDto dto) {
        return Guardian.builder()
        		.guardianName(dto.guardianName())
        		.guardianPhone(dto.guardianPhone())
        		.relationship(dto.relationship())
        		.guardianNote(dto.guardianNote())
        		.build();
    }
	
    private Senior dtoToSenior(SeniorRequestDto dto) {
        if (dto == null)
            return null;
        Guardian guardian = dtoToGuardian(dto);
        MedicalInfo medicalInfo = dtoToMedicalInfo(dto);
        Senior senior = Senior.builder()
        		.name(dto.name())
        		.birthDate(dto.birthDate())
        		.sex(dto.sex())
        		.phone(dto.phone())
        		.address(dto.address())
        		.note(dto.note())
        		.guardian(guardian)
        		.medicalInfo(medicalInfo)
        		.build();
        return senior;
    }
    
    private void updateSenior(Senior senior, SeniorRequestDto dto) {
    	senior.updatePersonalInfo(dto.name(), dto.birthDate(), dto.sex(),
    			dto.phone(), dto.address(), dto.note());
    	senior.updateGuardianInfo(dtoToGuardian(dto));
    	senior.updateMedicalInfo(dtoToMedicalInfo(dto));
    }
}
