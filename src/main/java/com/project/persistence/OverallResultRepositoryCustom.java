package com.project.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.dto.request.OverallResultSearchCondition;
import com.project.dto.response.OverallResultListResponseDto;
import com.project.dto.response.RecentUrgentResultDto;

public interface OverallResultRepositoryCustom {
    Page<OverallResultListResponseDto> searchOverallResults(OverallResultSearchCondition condition, Pageable pageable);
    List<RecentUrgentResultDto> findRecentUrgentResults();
}