package com.project.persistence;

import com.project.dto.request.SeniorSearchCondition;
import com.project.dto.response.SeniorListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeniorRepositoryCustom {
    Page<SeniorListResponseDto> searchSeniors(SeniorSearchCondition condition, Pageable pageable);
}