package com.project.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.senior.Gu;
import com.project.domain.senior.Haengjeongdong;
import com.project.dto.response.DongDto;
import com.project.dto.response.GuDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdministrativeDistrictService {

    public List<GuDto> getAdministrativeDistricts() {
        return Arrays.stream(Gu.values())
                .map(gu -> {
                    List<DongDto> dongDtos = Haengjeongdong.findAllByGu(gu).stream()
                            .map(dong -> new DongDto(dong.name(), dong.getKoreanName()))
                            .collect(Collectors.toList());

                    return new GuDto(gu.name(), gu.getKoreanName(), dongDtos);
                })
                .collect(Collectors.toList());
    }
}