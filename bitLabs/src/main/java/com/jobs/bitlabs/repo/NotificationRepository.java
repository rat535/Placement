package com.jobs.bitlabs.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobs.bitlabs.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByUserIdAndIsReadFalse(Long userId);
}
