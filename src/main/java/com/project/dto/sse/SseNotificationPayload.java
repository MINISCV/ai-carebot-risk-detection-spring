package com.project.dto.sse;

import com.project.domain.notification.Notification;
import com.project.domain.notification.NotificationType;

import java.time.LocalDateTime;

public record SseNotificationPayload(
        Long notificationId,
        NotificationType type,
        String resourceId,
        String message,
        boolean isRead,
        LocalDateTime createdAt
) {
    public static SseNotificationPayload from(Notification notification) {
        return new SseNotificationPayload(
                notification.getId(),
                notification.getNotificationType(),
                notification.getRelatedResourceId(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}