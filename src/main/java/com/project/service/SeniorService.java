package com.project.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.domain.analysis.OverallResult;
import com.project.domain.senior.Address;
import com.project.domain.senior.Doll;
import com.project.domain.senior.Guardian;
import com.project.domain.senior.MedicalInfo;
import com.project.domain.senior.Senior;
import com.project.dto.request.SeniorRequestDto;
import com.project.dto.request.SeniorSearchCondition;
import com.project.dto.response.RecentOverallResultDto;
import com.project.dto.response.SeniorDetailResponseDto;
import com.project.dto.response.SeniorListResponseDto;
import com.project.dto.response.SeniorResponseDto;
import com.project.persistence.DollRepository;
import com.project.persistence.SeniorRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeniorService {
	private final SeniorRepository seniorRepository;
	private final DollRepository dollRepository;

	@Value("${senior.photo.upload-path}")
    private String uploadPath;
	
	@Transactional
	public SeniorResponseDto createSenior(SeniorRequestDto requestDto, MultipartFile photo) {
		Doll doll = dollRepository.findByIdWithSenior(requestDto.dollId())
				.orElseThrow(() -> new EntityNotFoundException("인형 " + requestDto.dollId() + " 없음."));
		
		if (doll.getSenior() != null)
			throw new IllegalArgumentException("해당 인형은 이미 사용 중.");

		String photoFilename = savePhoto(photo);
		
		Senior senior = dtoToSenior(requestDto, photoFilename);
		senior.changeDoll(doll);
		seniorRepository.save(senior);
		
		return new SeniorResponseDto(senior);
	}

	@Transactional(readOnly = true)
    public Page<SeniorListResponseDto> searchSeniors(SeniorSearchCondition condition, Pageable pageable) {
        return seniorRepository.searchSeniors(condition, pageable);
    }
	
	@Transactional(readOnly = true)
    public SeniorDetailResponseDto getSeniorDetails(Long id) {
        Senior senior = seniorRepository.findDetailsById(id)
                .orElseThrow(() -> new EntityNotFoundException("시니어 " + id + "는 없음."));

        List<RecentOverallResultDto> recentResults = senior.getOverallResults().stream()
                .sorted(Comparator.comparing(OverallResult::getTimestamp).reversed())
                .limit(5)
                .map(RecentOverallResultDto::from)
                .collect(Collectors.toList());

        return new SeniorDetailResponseDto(senior, recentResults);
    }

	@Transactional
	public SeniorResponseDto updateSenior(Long id, SeniorRequestDto seniorDto, MultipartFile photo) {
		Senior existingSenior = seniorRepository.findByIdWithDoll(id)
				.orElseThrow(() -> new EntityNotFoundException("시니어 " + id + "는 없음."));

		if (!existingSenior.getDoll().getId().equals(seniorDto.dollId())) {
			Doll newDoll = dollRepository.findById(seniorDto.dollId()).orElseThrow(
					() -> new EntityNotFoundException("인형 " + seniorDto.dollId() + "없음."));
			if (newDoll.getSenior() != null)
				throw new IllegalArgumentException("해당 인형은 이미 사용 중.");
			existingSenior.changeDoll(newDoll);
		}
		
		String newPhotoFilename = existingSenior.getPhoto();
        if (photo != null && !photo.isEmpty()) {
            if (newPhotoFilename != null) {
                deletePhoto(newPhotoFilename);
            }
            newPhotoFilename = savePhoto(photo);
        }

		updateSenior(existingSenior, seniorDto, newPhotoFilename);
		 
		return new SeniorResponseDto(existingSenior);
	}

	@Transactional
	public void deleteSenior(Long id) {
		Senior senior = seniorRepository.findByIdWithDoll(id)
				.orElseThrow(() -> new EntityNotFoundException("시니어 " + id + "는 없음."));
		
		if (senior.getPhoto() != null) {
            deletePhoto(senior.getPhoto());
        }
		
		if (senior.getDoll() != null)
	        senior.changeDoll(null);
		seniorRepository.deleteById(id);
	}
	
	private String savePhoto(MultipartFile photo) {
        if (photo == null || photo.isEmpty()) {
            return null;
        }

        try {
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String originalFilename = photo.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String savedFilename = UUID.randomUUID().toString() + extension;

            Path destination = Paths.get(uploadPath, savedFilename);
            photo.transferTo(destination);

            return savedFilename;
        } catch (IOException e) {
            log.error("사진 파일 저장 실패", e);
            throw new RuntimeException("사진 파일을 저장하는 중 오류가 발생했습니다.", e);
        }
    }

    private void deletePhoto(String filename) {
        if (filename == null) return;
        try {
            Path fileToDelete = Paths.get(uploadPath, filename);
            Files.deleteIfExists(fileToDelete);
        } catch (IOException e) {
            log.error("사진 파일 삭제 실패: " + filename, e);
        }
    }

    private MedicalInfo dtoToMedicalInfo(SeniorRequestDto dto) {
        return MedicalInfo.builder()
        		.diseases(dto.diseases())
        		.medications(dto.medications())
        		.note(dto.diseaseNote())
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
    
    private Address dtoToAddress(SeniorRequestDto dto) {
        return Address.builder()
        		.address(dto.address())
        		.detail(dto.addressDetail())
        		.gu(dto.gu())
        		.dong(dto.dong())
        		.build();
    }
	
    private Senior dtoToSenior(SeniorRequestDto dto, String photoFilename) {
        if (dto == null)
            return null;
        Guardian guardian = dtoToGuardian(dto);
        MedicalInfo medicalInfo = dtoToMedicalInfo(dto);
        Address address = dtoToAddress(dto);
        Senior senior = Senior.builder()
        		.name(dto.name())
        		.photo(photoFilename)
        		.birthDate(dto.birthDate())
        		.sex(dto.sex())
        		.residence(dto.residence())
        		.phone(dto.phone())
        		.address(address)
        		.note(dto.note())
        		.guardian(guardian)
        		.medicalInfo(medicalInfo)
        		.build();
        return senior;
    }
    
    private void updateSenior(Senior senior, SeniorRequestDto dto, String newPhotoFilename) {
        Address address = dtoToAddress(dto);
    	senior.updatePersonalInfo(dto.name(), newPhotoFilename, dto.birthDate(), dto.sex(), dto.residence(),
    			dto.phone(), address, dto.note());
    	senior.updateGuardianInfo(dtoToGuardian(dto));
    	senior.updateMedicalInfo(dtoToMedicalInfo(dto));
    }
}
