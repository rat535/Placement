package com.jobs.bitlabs.service;

import com.jobs.bitlabs.enums.ApplicationStatus;

public interface JobApplicationService {

	ApplicationStatus getJobApplicationStatus(Long userId, String jobId);
	void updateApplicationStatus(Long userId, String jobId, ApplicationStatus status);
}
