package com.project.dto.response;

import com.project.domain.notification.Notification;
import com.project.domain.notification.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponseDto(
        Long notificationId,
        NotificationType type,
        String resourceId,
        String message,
        boolean isRead,
        LocalDateTime createdAt
) {
    public static NotificationResponseDto from(Notification notification) {
        return new NotificationResponseDto(
                notification.getId(),
                notification.getNotificationType(),
                notification.getRelatedResourceId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}