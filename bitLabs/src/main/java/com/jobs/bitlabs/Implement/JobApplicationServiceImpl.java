package com.jobs.bitlabs.Implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobs.bitlabs.entity.CompanyJob;
import com.jobs.bitlabs.entity.Users;
import com.jobs.bitlabs.enums.ApplicationStatus;
import com.jobs.bitlabs.repo.CompanyJobRepo;
import com.jobs.bitlabs.repo.UserRepo;
import com.jobs.bitlabs.service.JobApplicationService;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

	@Autowired
	private UserRepo usersRepository;

	@Autowired
	private CompanyJobRepo companyJobRepository;

	@Override
	public ApplicationStatus getJobApplicationStatus(Long userId, String jobId) {
		Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CompanyJob job = companyJobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        if (user.getAppliedJobs().contains(job)) {
            return user.getApplicationStatus(); // Return the current application status
        } else {
            throw new RuntimeException("User has not applied for this job");
        }
	}

	@Override
	public void updateApplicationStatus(Long userId, String jobId, ApplicationStatus status) {
		Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CompanyJob job = companyJobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        if (user.getAppliedJobs().contains(job)) {
            user.setApplicationStatus(status);
            usersRepository.save(user); // Save updated status
        } else {
            throw new RuntimeException("User has not applied for this job");
        }

	}

}
