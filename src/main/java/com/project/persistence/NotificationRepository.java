package com.project.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.domain.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	List<Notification> findByRecipientUsernameOrderByCreatedAtDesc(String username);
}
