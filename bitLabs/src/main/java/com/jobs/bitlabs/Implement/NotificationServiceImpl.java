package com.jobs.bitlabs.Implement;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobs.bitlabs.dto.NotificationDto;

import com.jobs.bitlabs.entity.Notification;

import com.jobs.bitlabs.enums.NotificationType;
import com.jobs.bitlabs.repo.NotificationRepository;
import com.jobs.bitlabs.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Notification dtoToNotification(NotificationDto notDto) {
		Notification user = this.modelMapper.map(notDto, Notification.class);
		return user;

	}

	public NotificationDto UserToDto(Notification user) {
		NotificationDto userDto = this.modelMapper.map(user, NotificationDto.class);
		return userDto;
	}

	@Override
	public void addJobRecommendationNotification(Long userId, String jobTitle) {
		String message = "You have successfully applied for the job: " + jobTitle;
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setMessage(Arrays.asList(message));
		notificationDto.setUserId(userId);
		notificationDto.setTimestamp(new Date());
		notificationDto.setType(NotificationType.JOB_APPLIED);
		
		Notification notification = this.dtoToNotification(notificationDto);
		notificationRepository.save(notification);
	}

	@Override
	public void addJobApplicationNotification(Long userId, String jobTitle) {
		String message = "You applied for the job: " + jobTitle;

		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setMessage(Arrays.asList(message));
		notificationDto.setUserId(userId);
		notificationDto.setTimestamp(new Date());
		notificationDto.setType(NotificationType.JOB_APPLIED);
		Notification notification = this.dtoToNotification(notificationDto);
		notification.setRead(false);

		notificationRepository.save(notification);

	}

	@Override
	public void addJobStatusUpdateNotification(Long userId, String jobTitle, String newStatus) {
		String message = String.format("Your application for '%s' has been updated to: %s", jobTitle, newStatus);
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setMessage(Arrays.asList(message));
		notificationDto.setUserId(userId);
		notificationDto.setTimestamp(new Date());
		notificationDto.setType(NotificationType.STATUS_UPDATE);
		Notification notification = this.dtoToNotification(notificationDto);
		notificationRepository.save(notification);
	}

	@Override
	public List<Notification> getUnreadNotifications(Long userId) {
		return notificationRepository.findByUserIdAndIsReadFalse(userId);
	}

}
