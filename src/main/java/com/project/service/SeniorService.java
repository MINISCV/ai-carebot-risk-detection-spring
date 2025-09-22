package com.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.Doll;
import com.project.domain.Senior;
import com.project.dto.SeniorDto;
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
	public Senior createSenior(SeniorDto seniorDto) {
		Doll doll = dollRepository.findById(seniorDto.dollId())
				.orElseThrow(() -> new EntityNotFoundException("인형 " + seniorDto.dollId() + " 없음."));

		if (doll.getSenior() != null)
			throw new IllegalStateException("해당 인형은 이미 사용 중.");

		Senior senior = Senior.builder()
				.name(seniorDto.name())
				.birthDate(seniorDto.birthDate())
				.sex(seniorDto.sex())
				.phone(seniorDto.phone())
				.address(seniorDto.address())
				.note(seniorDto.note())
				.guardianName(seniorDto.guardianName())
				.guardianPhone(seniorDto.guardianPhone())
				.relationship(seniorDto.relationship())
				.guardianNote(seniorDto.guardianNote())
				.diseases(seniorDto.diseases())
				.medications(seniorDto.medications())
				.build();

		senior.changeDoll(doll);
		
		return seniorRepository.save(senior);
	}

	@Transactional(readOnly = true)
	public Optional<Senior> getSeniorById(Long id) {
		return seniorRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public List<Senior> getAllSeniors() {
		return seniorRepository.findAll();
	}

	@Transactional
	public Senior updateSenior(Long id, SeniorDto seniorDto) {
		Senior existingSenior = seniorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("시니어 " + id + "는 없음."));

		if (!existingSenior.getDoll().getId().equals(seniorDto.dollId())) {
			Doll newDoll = dollRepository.findById(seniorDto.dollId()).orElseThrow(
					() -> new EntityNotFoundException("인형 " + seniorDto.dollId() + "없음."));
			if (newDoll.getSenior() != null)
				throw new IllegalStateException("해당 인형은 이미 사용 중.");
			existingSenior.changeDoll(newDoll);
		}

		 existingSenior.update(seniorDto);
		 
		return existingSenior;
	}

	@Transactional
	public void deleteSenior(Long id) {
		if (!seniorRepository.existsById(id))
			throw new EntityNotFoundException(id + " 시니어는 없음.");
		seniorRepository.deleteById(id);
	}
}
