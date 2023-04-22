package com.yash.ems.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.yash.ems.model.User;
import com.yash.ems.repo.UserRepository;

@SpringBootTest(classes= {UserDetailsServiceImplTest.class})
class UserDetailsServiceImplTest {
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserDetailsServiceImpl userDetails;
	
	@Test
	public void loadUserByUsername_success() {
		User user = new User();
	    user.setUsername("ShubhamBhake");
		    user.setEmail("shubhambhake@gmail.com");
		    user.setPassword("123");
		    user.setFirstName("Shubham");
		    user.setLastName("Bhake");
		    user.setPhone("123");
		    user.setEmpDesignation("Competency Member");
		    
		    String userName="ShubhamBhake";
		    
		    when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
		    
		    assertEquals(user,userDetails.loadUserByUsername(user.getUsername()));	
	}
}
