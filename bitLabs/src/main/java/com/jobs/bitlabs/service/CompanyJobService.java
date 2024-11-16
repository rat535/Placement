package com.jobs.bitlabs.service;

import java.util.List;
import java.util.Set;

import com.jobs.bitlabs.dto.CompanyJobDto;
import com.jobs.bitlabs.dto.UserDto;

public interface CompanyJobService {

	CompanyJobDto postJob(CompanyJobDto companyjobdto);
	
	List<CompanyJobDto> getAllJobs();
	
	void deleteByJobId(String JobId);
	
	Set<UserDto> getUsersForJob(String jobId);
}
