package com.jobs.bitlabs.extra;

import java.util.Date;

import com.jobs.bitlabs.enums.PefferedLocation;

public class StatusOfTheJob {

	private String jobTitle;
    private String organizationName;
    private PefferedLocation location;  // Assuming this is your location enum
    private Date jobPostedDate;
    private double packageOffered;
    private int requiredExperience;
    private String organizationLogoUrl;
    private String jobDetailsLink;
    private ApplicationJourneyStatus currentStatus;

    // Milestone stages
    public enum ApplicationJourneyStatus {
        APPLIED,
        SCREENING,
        SHORTLISTED,
        INTERVIEWING,
        SELECTED
    }

	public StatusOfTheJob() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatusOfTheJob(String jobTitle, String organizationName, PefferedLocation location, Date jobPostedDate,
			double packageOffered, int requiredExperience, String organizationLogoUrl, String jobDetailsLink,
			ApplicationJourneyStatus currentStatus) {
		super();
		this.jobTitle = jobTitle;
		this.organizationName = organizationName;
		this.location = location;
		this.jobPostedDate = jobPostedDate;
		this.packageOffered = packageOffered;
		this.requiredExperience = requiredExperience;
		this.organizationLogoUrl = organizationLogoUrl;
		this.jobDetailsLink = jobDetailsLink;
		this.currentStatus = currentStatus;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public PefferedLocation getLocation() {
		return location;
	}

	public void setLocation(PefferedLocation location) {
		this.location = location;
	}

	public Date getJobPostedDate() {
		return jobPostedDate;
	}

	public void setJobPostedDate(Date jobPostedDate) {
		this.jobPostedDate = jobPostedDate;
	}

	public double getPackageOffered() {
		return packageOffered;
	}

	public void setPackageOffered(double packageOffered) {
		this.packageOffered = packageOffered;
	}

	public int getRequiredExperience() {
		return requiredExperience;
	}

	public void setRequiredExperience(int requiredExperience) {
		this.requiredExperience = requiredExperience;
	}

	public String getOrganizationLogoUrl() {
		return organizationLogoUrl;
	}

	public void setOrganizationLogoUrl(String organizationLogoUrl) {
		this.organizationLogoUrl = organizationLogoUrl;
	}

	public String getJobDetailsLink() {
		return jobDetailsLink;
	}

	public void setJobDetailsLink(String jobDetailsLink) {
		this.jobDetailsLink = jobDetailsLink;
	}

	public ApplicationJourneyStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(ApplicationJourneyStatus currentStatus) {
		this.currentStatus = currentStatus;
	}
    
    
}
