package com.project.domain.notification;

import com.project.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_username")
    private Member recipient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    private String message;

    private String relatedResourceId;

    @Column(nullable = false)
    private boolean isRead = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Notification(Member recipient, NotificationType notificationType, String message, String relatedResourceId) {
        this.recipient = recipient;
        this.notificationType = notificationType;
        this.message = message;
        this.relatedResourceId = relatedResourceId;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}