package com.project.persistence;

import com.project.dto.request.OverallResultSearchCondition;
import com.project.dto.response.OverallResultListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OverallResultRepositoryCustom {
    Page<OverallResultListResponseDto> searchOverallResults(OverallResultSearchCondition condition, Pageable pageable);
}