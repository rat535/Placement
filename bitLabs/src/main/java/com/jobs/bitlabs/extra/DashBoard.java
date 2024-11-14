package com.jobs.bitlabs.extra;

public class DashBoard {

	private Long appliedJobCount;
	private int recommendedJobCount;

	
	
	public DashBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Constructors, getters, and setters
	public DashBoard(Long appliedJobCount, int recommendedJobCount) {
		this.appliedJobCount = appliedJobCount;
		this.recommendedJobCount = recommendedJobCount;
	}

	public Long getAppliedJobCount() {
		return appliedJobCount;
	}

	public void setAppliedJobCount(Long appliedJobCount) {
		this.appliedJobCount = appliedJobCount;
	}

	public int getRecommendedJobCount() {
		return recommendedJobCount;
	}

	public void setRecommendedJobCount(int recommendedJobCount) {
		this.recommendedJobCount = recommendedJobCount;
	}
}
