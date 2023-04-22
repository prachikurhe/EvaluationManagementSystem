package com.yash.ems;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import static org.mockito.BDDMockito.given;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import com.yash.ems.model.User;
import com.yash.ems.repo.UserRepository;
import com.yash.ems.service.impl.UserDetailsServiceImpl;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetUserTestCaseLoginTest {
	
	@Mock
    private UserRepository userRepository;
	
	@InjectMocks
	private UserDetailsServiceImpl userDetailsServiceImpl =new UserDetailsServiceImpl();
	

	private User user1;
	
//	@BeforeEach
//    public void setup(){
//        userRepository = Mockito.mock(UserRepository.class);
//        userDetailsServiceImpl = new UserDetailsServiceImpl(userRepository);
//		user1 = User.builder().id(123L).username("ShubhamBhake55")
//				.password("123").firstName("Shubham").lastName("bhake")
//				.phone("123").email("shubham@gmail.com").empDesignation("Competncy Member")
//                .build();
//    }
	
	@Test
    public void loadUserByUsernameTest(){
	
//		user1 = User.builder().id(123L).username("ShubhamBhake55")
//				.password("123").firstName("Shubham").lastName("bhake")
//				.phone("123").email("shubham@gmail.com").empDesignation("Competncy Member")
//                .build();
		
		
		given(userRepository.findByUsername("ShubhamBhake55")).willReturn(user1);
		//willReturn(Optional.of(user1));

        // when
		UserDetails userfound= userDetailsServiceImpl.loadUserByUsername(user1.getUsername());
		

        // then
        assertThat(userfound).isNotNull();


    }

	



	
    }
	

		




