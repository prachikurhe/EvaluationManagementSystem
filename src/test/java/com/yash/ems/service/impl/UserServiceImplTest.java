package com.yash.ems.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.yash.ems.model.Role;
import com.yash.ems.model.User;
import com.yash.ems.model.UserRole;
import com.yash.ems.repo.RoleRepository;
import com.yash.ems.repo.UserRepository;

@SpringBootTest(classes = { UserServiceImplTest.class })
class UserServiceImplTest {

	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;

	@InjectMocks
	UserServiceImpl userService;

	@Test
	public void createUserTest_success() throws Exception {
		User user = new User();
	    user.setUsername("sumit");
		    user.setEmail("ShubhamBhake@gmail.com");
		    user.setPassword("123");
		    user.setFirstName("Shubham");
		    user.setLastName("Bhake");
		    user.setPhone("123");
		    user.setEmpDesignation("ADMIN");
		    
		    Role role = new Role();
		    role.setRoleId(44L);
	   		role.setRoleName("Competency Member");
	   		
	   	 Set<UserRole> roles = new HashSet<>();
	   		
	   		UserRole userRole = new UserRole();
	        
	        userRole.setUser(user);
	        userRole.setRole(role);

	        roles.add(userRole);
		    
		    when(userRepository.findByUsername("shubham")).thenReturn(user);
//		    verify(roleRepository,times(1)).save(role);
		    when(userRepository.save(user)).thenReturn(user);
		    
		    
		    assertEquals(user, userService.createUser(user, roles));
		
	}

}
