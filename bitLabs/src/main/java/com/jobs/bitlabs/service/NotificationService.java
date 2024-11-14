package com.jobs.bitlabs.service;

import java.util.List;

import com.jobs.bitlabs.entity.Notification;

public interface NotificationService {

	void addJobRecommendationNotification(Long userId, String jobTitle);

	void addJobApplicationNotification(Long userId, String jobTitle);

	List<Notification> getUnreadNotifications(Long userId);

	void addJobStatusUpdateNotification(Long userId, String jobTitle, String newStatus);
}
