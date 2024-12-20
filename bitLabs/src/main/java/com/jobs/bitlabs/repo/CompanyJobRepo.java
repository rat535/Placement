package com.jobs.bitlabs.repo;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jobs.bitlabs.entity.CompanyJob;
import com.jobs.bitlabs.entity.Users;


@Repository
public interface CompanyJobRepo  extends JpaRepository<CompanyJob, String>{

	
	@Query("SELECT c.users FROM CompanyJob c WHERE c.JobId = :jobId")
	Set<Users> findUsersByJobId(@Param("jobId") String jobId);
}
