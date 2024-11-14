package com.jobs.bitlabs.entity;

import java.util.Date;
import java.util.HashSet;

import java.util.Set;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import com.jobs.bitlabs.enums.PefferedLocation;
import com.jobs.bitlabs.enums.Qualifaction;
import com.jobs.bitlabs.enums.Skills;

@Entity
public class CompanyJob {

	@Id
	@Column(name = "JobId", updatable = false, nullable = false)
	private String JobId;
	private String JobTitle;
	@Column(length = 5000)
	private String JobDescription;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	private Date jobposteddate;
	private String CompanyId;
	private Qualifaction Qualification;
	private Long ExperienceMin;
	private Long ExperienceMax;
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<Skills> Skills;
	private Long SalaryMin;
	private Long SalaryMax;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<PefferedLocation> PreferdJobLocation;

	private String JobType;
	private Boolean Status;

	@ManyToMany(mappedBy = "appliedJobs")
	@JsonIgnore
	private Set<Users> users = new HashSet<>();

	public CompanyJob() {
		super();

	}

	public CompanyJob(String jobId, String jobTitle, String jobDescription, Date jobposteddate, String companyId,
			Qualifaction qualification, Long experienceMin, Long experienceMax, Set<com.jobs.bitlabs.enums.Skills> skills,
			Long salaryMin, Long salaryMax, Set<PefferedLocation> preferdJobLocation, String jobType, Boolean status,
			Set<Users> users) {
		super();
		JobId = jobId;
		JobTitle = jobTitle;
		JobDescription = jobDescription;
		this.jobposteddate = jobposteddate;
		CompanyId = companyId;
		Qualification = qualification;
		ExperienceMin = experienceMin;
		ExperienceMax = experienceMax;
		Skills = skills;
		SalaryMin = salaryMin;
		SalaryMax = salaryMax;
		PreferdJobLocation = preferdJobLocation;
		JobType = jobType;
		Status = status;
		this.users = users;
	}

	public String getJobId() {
		return JobId;
	}

	public void setJobId(String jobId) {
		JobId = jobId;
	}

	public String getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}

	public String getJobDescription() {
		return JobDescription;
	}

	public void setJobDescription(String jobDescription) {
		JobDescription = jobDescription;
	}

	public Date getJobposteddate() {
		return jobposteddate;
	}

	public void setJobposteddate(Date jobposteddate) {
		this.jobposteddate = jobposteddate;
	}

	public String getCompanyId() {
		return CompanyId;
	}

	public void setCompanyId(String companyId) {
		CompanyId = companyId;
	}

	public Qualifaction getQualification() {
		return Qualification;
	}

	public void setQualification(Qualifaction qualification) {
		Qualification = qualification;
	}

	public Long getExperienceMin() {
		return ExperienceMin;
	}

	public void setExperienceMin(Long experienceMin) {
		ExperienceMin = experienceMin;
	}

	public Long getExperienceMax() {
		return ExperienceMax;
	}

	public void setExperienceMax(Long experienceMax) {
		ExperienceMax = experienceMax;
	}

	public Long getSalaryMin() {
		return SalaryMin;
	}

	public void setSalaryMin(Long salaryMin) {
		SalaryMin = salaryMin;
	}

	public Long getSalaryMax() {
		return SalaryMax;
	}

	public void setSalaryMax(Long salaryMax) {
		SalaryMax = salaryMax;
	}

	public Set<Skills> getSkills() {
		return Skills;
	}

	public void setSkills(Set<Skills> skills) {
		Skills = skills;
	}

	public Set<PefferedLocation> getPreferdJobLocation() {
		return PreferdJobLocation;
	}

	public void setPreferdJobLocation(Set<PefferedLocation> preferdJobLocation) {
		PreferdJobLocation = preferdJobLocation;
	}

	public String getJobType() {
		return JobType;
	}

	public void setJobType(String jobType) {
		JobType = jobType;
	}

	public Boolean getStatus() {
		return Status;
	}

	public void setStatus(Boolean status) {
		Status = status;
	}

	public Set<Users> getUsers() {
		return users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}

}
