package com.jobs.bitlabs.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobs.bitlabs.dto.CompanyJobDto;
import com.jobs.bitlabs.dto.UserDto;
import com.jobs.bitlabs.enums.ApplicationStatus;
import com.jobs.bitlabs.enums.PefferedLocation;
import com.jobs.bitlabs.enums.Qualifaction;
import com.jobs.bitlabs.service.CompanyJobService;
import com.jobs.bitlabs.service.JobApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.jobs.bitlabs.enums.Skills;

@Tag(name = "JobS", description = "Company Jobs ")
@RestController
@RequestMapping("companyJob")
public class CompanyJobController {

	@Autowired
	private CompanyJobService companyjobservice;

	@Autowired
	private JobApplicationService jobApplicationService;

	@Operation(summary = "create a job")
	@PostMapping("job")
	public CompanyJobDto createjob(@RequestParam String JobId, @RequestParam String JobTitle,
			@RequestParam String JobDescription,
			@RequestParam @Parameter(description = "Date in the format dd-MM-yyyy", schema = @Schema(type = "string", format = "date", example = "13-06-2002")) @DateTimeFormat(pattern = "dd-MM-yyyy") Date jobposteddate,
			@RequestParam String CompanyId, @RequestParam Qualifaction Qualification, @RequestParam Long ExperienceMin,
			@RequestParam Long ExperienceMax, @RequestParam Set<Skills> Skills, @RequestParam Long SalaryMin,
			@RequestParam Long SalaryMax, @RequestParam Set<PefferedLocation> PreferedJobLocation,
			@RequestParam String JobType, @RequestParam Boolean Status

	) {

		return companyjobservice.postJob(new CompanyJobDto(JobId, JobTitle, JobDescription, jobposteddate, CompanyId,
				Qualification, ExperienceMin, ExperienceMax, Skills, SalaryMin, SalaryMax, PreferedJobLocation, JobType,
				Status));

	}

	@Operation(summary = "get all the job")
	@GetMapping("getAllJobs")
	public List<CompanyJobDto> getAlljobs() {
		return companyjobservice.getAllJobs();
	}

	@Operation(summary = "Delete")
	@DeleteMapping("deleteJob")
	public void deleteJobById(@RequestParam String Jobid) {
		companyjobservice.deleteByJobId(Jobid);
	}

	@Operation(summary = "status")
	@GetMapping("/status/{userId}/{jobId}")
	public ApplicationStatus getApplicationStatus(@PathVariable Long userId, @PathVariable String jobId) {
		return jobApplicationService.getJobApplicationStatus(userId, jobId);
	}

	@Operation(summary = "updated status")
	@PutMapping("/status/{userId}/{jobId}")
	public void updateApplicationStatus(@PathVariable Long userId, @PathVariable String jobId,
			@RequestParam ApplicationStatus status) {
		jobApplicationService.updateApplicationStatus(userId, jobId, status);
	}

	@Operation(summary = "Get all users who applied for a job")
	@GetMapping("/job/{jobId}/users")
	public ResponseEntity<Set<UserDto>> getUsersByJobId(@PathVariable String jobId) {
		Set<UserDto> users = companyjobservice.getUsersForJob(jobId);
		return ResponseEntity.ok(users);
	}
}
