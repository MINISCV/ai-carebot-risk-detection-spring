package com.project.service;

import com.project.domain.analysis.Risk;
import com.project.dto.response.DashboardResponseDto;
import com.project.dto.response.RecentUrgentResultDto;
import com.project.dto.response.StateCountDto;
import com.project.persistence.OverallResultRepository;
import com.project.persistence.SeniorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final SeniorRepository seniorRepository;
    private final OverallResultRepository overallResultRepository;

    @Transactional(readOnly = true)
    public DashboardResponseDto getDashboardData() {
        long totalSeniors = seniorRepository.count();
        List<Object[]> stateCountsResult = seniorRepository.countSeniorsByState();
        
        Map<Risk, Long> countsMap = stateCountsResult.stream()
                .collect(Collectors.toMap(
                        row -> (Risk) row[0],
                        row -> (Long) row[1]
                ));

        StateCountDto stateCountDto = new StateCountDto(
                totalSeniors,
                countsMap.getOrDefault(Risk.POSITIVE, 0L),
                countsMap.getOrDefault(Risk.DANGER, 0L),
                countsMap.getOrDefault(Risk.CRITICAL, 0L),
                countsMap.getOrDefault(Risk.EMERGENCY, 0L)
        );

        List<RecentUrgentResultDto> recentUrgentResults = overallResultRepository.findRecentUrgentResults();

        return new DashboardResponseDto(stateCountDto, recentUrgentResults);
    }
}