package com.project.dto.response;

import java.util.List;

public record DashboardResponseDto(
        StateCountDto stateCount,
        List<RecentUrgentResultDto> recentUrgentResults
) {
}