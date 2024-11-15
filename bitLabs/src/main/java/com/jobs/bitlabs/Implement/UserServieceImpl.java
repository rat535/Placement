package com.jobs.bitlabs.Implement;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobs.bitlabs.dto.CompanyJobDto;
import com.jobs.bitlabs.dto.UserDto;
import com.jobs.bitlabs.entity.CompanyJob;
import com.jobs.bitlabs.entity.Users;
import com.jobs.bitlabs.enums.BTechSpecialization;
import com.jobs.bitlabs.enums.DegreeSpecialization;
import com.jobs.bitlabs.enums.DiplomaSpecialization;
import com.jobs.bitlabs.enums.IntermediateSpecialization;
import com.jobs.bitlabs.enums.MCASpecialization;
import com.jobs.bitlabs.enums.Qualifaction;
import com.jobs.bitlabs.exception.CustomException;
import com.jobs.bitlabs.extra.DashBoard;
import com.jobs.bitlabs.repo.CompanyJobRepo;

import com.jobs.bitlabs.repo.UserRepo;
import com.jobs.bitlabs.service.NotificationService;
import com.jobs.bitlabs.service.UserService;

@Service
public class UserServieceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CompanyJobRepo companyJobRepo;

	@Autowired
	private NotificationService notificationService;
	
	
	
	public Users dtoToUser(UserDto userDto) {
		Users user = this.modelMapper.map(userDto, Users.class);
		return user;

	}

	public UserDto UserToDto(Users user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	public CompanyJob dtoToJob(CompanyJobDto jobDto) {
		CompanyJob job = this.modelMapper.map(jobDto, CompanyJob.class);
		return job;

	}

	public CompanyJobDto JobToDto(CompanyJob job) {
		CompanyJobDto jobDto = this.modelMapper.map(job, CompanyJobDto.class);
		return jobDto;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		Users user = this.dtoToUser(userDto);
		Users savedUser = this.userRepo.save(user);
		return this.UserToDto(savedUser);
	}

	@Override
	public UserDto getUserById(Long userId) {

		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new CustomException("User not found with id: " + userId));
		return this.UserToDto(user);
	}

	@Override
	public boolean isValidSpecialization(Qualifaction qualification, String specialization) {

		switch (qualification) {
		case BTECH:
			return BTechSpecialization.valueOf(specialization) != null;
		case MCA:
			return MCASpecialization.valueOf(specialization) != null;
		case DEGREE:
			return DegreeSpecialization.valueOf(specialization) != null;
		case INTERMEDIATE:
			return IntermediateSpecialization.valueOf(specialization) != null;
		case DIPLOMA:
			return DiplomaSpecialization.valueOf(specialization) != null;
		default:
			return false;
		}

	}

	@Override
	public UserDto updateUser(Long userId, UserDto userDto) {
		// TODO Auto-generated method stub
		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new CustomException("User not found with id: " + userId));
		user.setPreferdJobLocation(userDto.getPreferdJobLocation());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setWhatsapp(userDto.getWhatsapp());
		user.setQualification(userDto.getQualification());
		user.setSpecialization(userDto.getSpecialization());

		user.setTotalExperience(userDto.getTotalExperience());
		user.setSkills(userDto.getSkills());
		user.setAddress(userDto.getAddress());
		user.setDateOfBirth(userDto.getDateOfBirth());
		user.setProfileImage(userDto.getProfileImage());
		user.setResume(userDto.getResume());

		userRepo.save(user);

		return this.UserToDto(user);
	}

	@Override
	public List<CompanyJobDto> getRecommendedJobs(Long userId) {
		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new CustomException("User not found with id: " + userId));
		List<CompanyJob> recommendedJobs = companyJobRepo.findAll().stream()
				.filter(job -> matchesUserProfile(user, job)).collect(Collectors.toList());
		return recommendedJobs.stream().map(this::JobToDto).collect(Collectors.toList());
	}

	private boolean matchesUserProfile(Users user, CompanyJob job) {

		boolean matchesQualification = job.getQualification() == user.getQualification();

		boolean matchesExperience = user.getTotalExperience() >= job.getExperienceMin()
				&& user.getTotalExperience() <= job.getExperienceMax();

		boolean matchesSkills = user.getSkills().containsAll(job.getSkills());

		boolean matchesLocation = !Collections.disjoint(user.getPreferdJobLocation(), job.getPreferdJobLocation());


		return matchesQualification && matchesExperience && matchesSkills && matchesLocation;
	}

	// ------------------------------------------  Applying for a job  ------------------------------------------

	@Override
	public boolean canApplyForJob(Long userId, String jobId) {
		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new CustomException("User not found with id: " + userId));

		
		CompanyJob job = this.companyJobRepo.findById(jobId)
				.orElseThrow(() -> new CustomException("Job not found with id: " + jobId));

		
		boolean matchesQualification = job.getQualification() == user.getQualification();

		boolean matchesExperience =(user.getTotalExperience() >= job.getExperienceMin());

		System.out.println(matchesExperience);
	
		

		boolean matchesSkills = user.getSkills().stream().anyMatch(job.getSkills()::contains);
		boolean matchesLocation = user.getPreferdJobLocation().stream()
                .anyMatch(job.getPreferdJobLocation()::contains);

		// If all conditions match, the user can apply  
		return matchesQualification && matchesSkills && matchesLocation && matchesExperience;
	}

	@Override
	public void applyForJob(Long userId, String jobId) {
		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new CustomException("User not found with id: " + userId));

		CompanyJob job = this.companyJobRepo.findById(jobId)
				.orElseThrow(() -> new CustomException("Job not found with id: " + jobId));

		// Check if the user can apply
		boolean canApply = canApplyForJob(userId, jobId);

		if (!canApply) {
			throw new CustomException("User's profile does not match the job requirements.");
		}
		user.getAppliedJobs().add(job);
		this.userRepo.save(user);
		
		 notificationService.addJobApplicationNotification(userId, job.getJobTitle());

	}

	@Override
	public DashBoard getDashboardData(Long userId) {
		Users user = userRepo.findById(userId)
				.orElseThrow(() -> new CustomException("User not found with id: " + userId));

		Long appliedJobCount = (long) user.getAppliedJobs().size();

		int recommendedJobCount = getRecommendedJobs(userId).size();

		return new DashBoard(appliedJobCount, recommendedJobCount);
	}
	
	
}
