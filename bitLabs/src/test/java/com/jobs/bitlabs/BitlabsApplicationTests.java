package com.jobs.bitlabs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobs.bitlabs.Implement.UserServieceImpl;
import com.jobs.bitlabs.dto.UserDto;
import com.jobs.bitlabs.entity.Users;
import com.jobs.bitlabs.exception.CustomException;
import com.jobs.bitlabs.repo.CompanyJobRepo;
import com.jobs.bitlabs.repo.UserRepo;
import com.jobs.bitlabs.service.NotificationService;

@SpringBootTest
class BitlabsApplicationTests {

	@Test
	void contextLoads() {
	}



	@InjectMocks
	private UserServieceImpl userService;
	
    @Mock 
    private UserRepo  userRepo;

    @Mock
    private CompanyJobRepo companyJobRepo;

    @Mock
    private NotificationService  notificationService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCreateUser() {
        UserDto  userDto = new UserDto();
        userDto.setName("Test User");

        Users  user = new Users();
        user.setName("Test User");

        when(modelMapper.map(userDto, Users.class)).thenReturn(user);
        when(userRepo.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.createUser(userDto);

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        verify(userRepo, times(1)).save(user);
    }
    
    @Test
    void testGetUserById_UserExists() {
        Users user = new Users();
        user.setId(1L);
        user.setName("Test User");

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDto.class)).thenReturn(new UserDto());

        UserDto result = userService.getUserById(1L);

        assertNotNull(result);
        verify(userRepo, times(1)).findById(1L);
    }
    
    @Test
    void testGetUserById_UserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomException.class, () -> userService.getUserById(1L));
        assertEquals("User not found with id: 1", exception.getMessage());
    }

}
