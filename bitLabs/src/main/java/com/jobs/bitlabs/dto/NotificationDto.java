package com.jobs.bitlabs.dto;

import java.util.Date;

import com.jobs.bitlabs.enums.NotificationType;



public class NotificationDto {

	private Long userId;
    private String message;
    private Date timestamp;
    private boolean isRead = false;

    
    private NotificationType type;


	public NotificationDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public NotificationDto(Long userId, String message, Date timestamp, boolean isRead, NotificationType type) {
		super();
		this.userId = userId;
		this.message = message;
		this.timestamp = timestamp;
		this.isRead = isRead;
		this.type = type;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	public boolean isRead() {
		return isRead;
	}


	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}


	public NotificationType getType() {
		return type;
	}


	public void setType(NotificationType type) {
		this.type = type;
	}
    
    

}
