package com.jobs.bitlabs.Implement;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobs.bitlabs.dto.CompanyJobDto;
import com.jobs.bitlabs.exception.CustomException;
import com.jobs.bitlabs.repo.CompanyJobRepo;
import com.jobs.bitlabs.repo.CompanyProfileRepository;
import com.jobs.bitlabs.service.CompanyJobService;
import com.jobs.bitlabs.dto.CompanyJobdtoMapper;
import com.jobs.bitlabs.dto.UserDto;
import com.jobs.bitlabs.entity.CompanyJob;
import com.jobs.bitlabs.entity.Users;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CompanyJobServiceImpl implements CompanyJobService {

	@Autowired
	private CompanyProfileRepository companyprofilerepo;
	@Autowired
	private CompanyJobRepo companyjobrepo;

	  @Autowired
	    private ModelMapper modelMapper;
	  
	  public UserDto UserToDto(Users user) {
	        return modelMapper.map(user, UserDto.class);
	    }
	private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9 ]");

	public CompanyJobDto postJob(CompanyJobDto companyjobdto) {

		if (SPECIAL_CHAR_PATTERN.matcher(companyjobdto.getJobId()).find()) {
			throw new CustomException("Job ID contains special characters: " + companyjobdto.getJobId());
		}
		if (companyjobrepo.existsById(companyjobdto.getJobId())) {
			throw new CustomException("Job ID already exists: " + companyjobdto.getJobId());
		}

		if (SPECIAL_CHAR_PATTERN.matcher(companyjobdto.getJobTitle()).find()) {
			throw new CustomException("Job Title contains special characters: " + companyjobdto.getJobTitle());
		}
		if (companyjobdto.getJobDescription().equals(null)) {
			throw new CustomException("Job Description is null: " + companyjobdto.getJobDescription());
		}
		if (!companyprofilerepo.existsById(companyjobdto.getCompanyId())) {
			throw new CustomException("Company not yet registered: please register " + companyjobdto.getCompanyId());
		}

		CompanyJob companyjob = CompanyJobdtoMapper.mapToCompanyJob(companyjobdto);
		CompanyJob savedcompanyjob = companyjobrepo.save(companyjob);
		return CompanyJobdtoMapper.mapToCompanyJobDto(savedcompanyjob);
	}

	public List<CompanyJobDto> getAllJobs() {

		List<CompanyJob> companyjobs = companyjobrepo.findAll();
		return companyjobs.stream().map((companyjob) -> CompanyJobdtoMapper.mapToCompanyJobDto(companyjob))
				.collect(Collectors.toList());
	}

	public void deleteByJobId(String JobId) {
		companyjobrepo.deleteById(JobId);
	}
	
	
	 @Override
	    public Set<UserDto> getUsersForJob(String jobId) {
	        Set<Users> users = companyjobrepo.findUsersByJobId(jobId);
	        if (users == null || users.isEmpty()) {
	            throw new CustomException("No users found for Job ID: " + jobId);
	        }
	        
	        return users.stream()
                    .map(this::UserToDto) // Use your mapping method
                    .collect(Collectors.toSet());
	    }
}