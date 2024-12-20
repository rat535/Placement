package com.jobs.bitlabs.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobs.bitlabs.dto.CompanyJobDto;
import com.jobs.bitlabs.dto.UserDto;
import com.jobs.bitlabs.entity.Notification;
import com.jobs.bitlabs.enums.ApplicationStatus;
import com.jobs.bitlabs.enums.PefferedLocation;
import com.jobs.bitlabs.enums.Qualifaction;
import com.jobs.bitlabs.enums.Skills;
import com.jobs.bitlabs.exception.CustomException;
import com.jobs.bitlabs.extra.Address;
import com.jobs.bitlabs.extra.DashBoard;
import com.jobs.bitlabs.service.FileServices;
import com.jobs.bitlabs.service.JobApplicationService;
import com.jobs.bitlabs.service.NotificationService;
import com.jobs.bitlabs.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Job Seekers", description = "profile ")
@RestController
@RequestMapping("/profile")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileServices fileServices;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private JobApplicationService jobApplicationService;

	@Value("${project.image}")
	private String path;

	@Value("${project.resume}")
	private String ps;

	// -----------------------------------------------------------------------------

//	@Operation(summary = "Creating New User")
//	@PostMapping("/")
//	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
//		
//		System.out.println("Qualification: " + userDto.getQualification());
//
//		if (!userService.isValidSpecialization(userDto.getQualification(), userDto.getSpecialization())) {
//			throw new CustomException("Invalid specialization for the selected qualification");
//		}
//
//		UserDto created = this.userService.createUser(userDto);
//		return new ResponseEntity<UserDto>(created, HttpStatus.CREATED);
//	}

	// -----------------------------------------------------------------------------

	@Operation(summary = "get the user by Id")
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> geetById(@PathVariable Long id) {
		UserDto userDto = this.userService.getUserById(id);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

	// -----------------------------------------------------------------------------

	@Operation(summary = "update All")
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
		if (!userService.isValidSpecialization(userDto.getQualification(), userDto.getSpecialization())) {
			throw new CustomException("Invalid specialization for the selected qualification");
		}
		UserDto updatedUser = userService.updateUser(id, userDto);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	// -----------------------------------------------------------------------------

	@Operation(summary = "update the profile pic")
	@PostMapping(value = "/image/{userId}", consumes = { "multipart/form-data" })
	public ResponseEntity<UserDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Long postId) throws IOException {

		UserDto postDto = this.userService.getUserById(postId);
		String fileName = this.fileServices.uploadImage(path, image);

		postDto.setProfileImage(fileName);
		UserDto updatePost = this.userService.updateUser(postId, postDto);

		return new ResponseEntity<UserDto>(updatePost, HttpStatus.OK);

	}

	// -----------------------------------------------------------------------------

	@Operation(summary = "update the resume")
	@PostMapping(value = "/resume/{userId}", consumes = { "multipart/form-data" })
	public ResponseEntity<UserDto> uploadResume(@RequestParam("resume") MultipartFile resume, @PathVariable Long userId)
			throws IOException {

		UserDto postDto = this.userService.getUserById(userId);
		String fileName = this.fileServices.uploadImage(ps, resume);

		postDto.setResume(fileName);
		UserDto updatePost = this.userService.updateUser(userId, postDto);

		return new ResponseEntity<UserDto>(updatePost, HttpStatus.OK);

	}

	// -----------------------------------------------------------------------------

	@Operation(summary = "Recomanded Jobs")
	@GetMapping("/user/{userId}")
	public List<CompanyJobDto> getRecommendedJobs(@PathVariable Long userId) {
		return userService.getRecommendedJobs(userId);
	}

	// -----------------------------------------------------------------------------

	@Operation(summary = "Apply for a job if user profile matches job requirements")
	@PostMapping("/user/{userId}/apply-job/{jobId}")
	public ResponseEntity<String> applyForJob(@PathVariable Long userId, @PathVariable String jobId) {
		try {
			userService.applyForJob(userId, jobId);

			return new ResponseEntity<>("Job application successful", HttpStatus.OK);
		} catch (CustomException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

//	@PutMapping("/{userId}/notifications/read/{notificationId}")
//	public ResponseEntity<String> markNotificationAsRead(@PathVariable Long userId, @PathVariable Long notificationId) {
//		try {
//			notificationService.markNotificationAsRead(userId, notificationId);
//			return new ResponseEntity<>("Notification marked as read", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>("Error marking notification as read", HttpStatus.BAD_REQUEST);
//		}
//	}

	// -----------------------------------------------------------------------------

	@GetMapping("/{userId}/dashboard")
	public ResponseEntity<DashBoard> getDashboardData(@PathVariable Long userId) {
		DashBoard dashboardData = userService.getDashboardData(userId);
		return ResponseEntity.ok(dashboardData);
	}

	// -----------------------------------------------------------------------------

	@Operation(summary = "Creating New User")
	@PostMapping("/createUser")
	public ResponseEntity<UserDto> createUser(@RequestParam String name, @RequestParam String email,
			@RequestParam String whatsapp, @RequestParam Qualifaction qualification,
			@RequestParam String specialization, @RequestParam int totalExperience,
			@RequestParam Set<PefferedLocation> preferdJobLocation, @RequestParam String addressLine,
			@RequestParam String city, @RequestParam String state, @RequestParam String pinCode,
			@RequestParam String alternateMobile,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth, @RequestParam Set<Skills> skills) {

		// Validate specialization based on qualification
		if (!userService.isValidSpecialization(qualification, specialization)) {
			throw new CustomException("Invalid specialization for the selected qualification");
		}

		Address address = new Address();
		address.setAddressLine(addressLine);
		address.setCity(city);
		address.setState(state);
		address.setPinCode(pinCode);
		address.setAlternateMobile(alternateMobile);
		// Create a new UserDto from the parameters
		UserDto userDto = new UserDto(name, email, whatsapp, qualification, specialization, totalExperience,
				preferdJobLocation, address, dateOfBirth, skills, null, null, null);

		// Create user using the service
		UserDto created = userService.createUser(userDto);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	// -----------------------------------------------------------------------------

	@Operation(summary = "Notification")
	@GetMapping("/{userId}/unread")
	public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long userId) {
		List<Notification> notifications = notificationService.getUnreadNotifications(userId);
		return ResponseEntity.ok(notifications);
	}
	// -----------------------------------------------------------------------------

	@Operation(summary = "get the status of the job")
	@GetMapping("/status/{userId}/{jobId}")
	public ResponseEntity<ApplicationStatus> getApplicationStatus(@PathVariable Long userId,
			@PathVariable String jobId) {
		ApplicationStatus applicationStatus = jobApplicationService.getJobApplicationStatus(userId, jobId);

		if (applicationStatus != null) {
			return ResponseEntity.ok(applicationStatus);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
