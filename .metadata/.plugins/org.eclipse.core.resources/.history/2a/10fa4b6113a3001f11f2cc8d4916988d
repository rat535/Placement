package com.jobs.bitlabs.entity;

import java.util.Date;

import com.jobs.bitlabs.enums.NotificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notification {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long userId;
	    private String message;
	    private Date timestamp;
	    private boolean isRead = false;

	    @Enumerated(EnumType.STRING)
	    private NotificationType type;

		public Notification() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Notification(Long id, Long userId, String message, Date timestamp, boolean isRead,
				NotificationType type) {
			super();
			this.id = id;
			this.userId = userId;
			this.message = message;
			this.timestamp = timestamp;
			this.isRead = isRead;
			this.type = type;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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
