package com.yash.ems;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.yash.ems.model.Role;
import com.yash.ems.model.User;
import com.yash.ems.model.UserRole;
import com.yash.ems.repo.RoleRepository;
import com.yash.ems.repo.UserRepository;
import com.yash.ems.service.impl.UserDetailsServiceImpl;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class CompetencyEmsProjectApplicationTests {
	
	

	
//	@Autowired
//	UserDetailsServiceImpl userDetailsServiceImpl;
	
//   @Autowired
//   private UserRepository userRepository;
//     
//
//    
//  @Test
//  public void createUserTest() {
//    	
//   	
//   	User user = new User();
//    user.setUsername("shubhamji");
//	    user.setEmail("ShubhamBhake@gmail.com");
//	    user.setPassword("123");
//	    user.setFirstName("Shubham");
//	    user.setLastName("Bhake");
//	    user.setPhone("123");
//	    user.setEmpDesignation("ADMIN");
//
//      User savedUser = userRepository.save(user);
//   	    
//       User existUser=userRepository.findByUsername("shubhamji");
//    	     
//   	assertThat(savedUser.getEmail()).isEqualTo(existUser.getEmail());
//    	   
//    }
    
//     @Test
//    public void loadUserByUsernameTest()throws UsernameNotFoundException {
//    	  
//    	    User user = new User();
//    	    
//   	    user.setUsername("shubham");
//   	    user.setEmail("ShubhamBhake@gmail.com");
//   	    user.setPassword("123");
//   	    user.setFirstName("Shubham");
//    	    user.setLastName("Bhake");
//    	    user.setPhone("123");
//    	    user.setEmpDesignation("ADMIN");
//    	    
//    	    Set<UserRole> roles = new HashSet<>();
//
//            Role role = new Role();
//            role.setRoleId(45L);
//            role.setRoleName("NORMAL");
//
//            UserRole userRole = new UserRole();
//            userRole.setUser(user);
//            userRole.setRole(role);
//
//            roles.add(userRole);
//            
//            user.setUserRoles(roles);
//    	
// 	    User savedUser = userRepository.save(user);
//    	 
//    	 UserDetailsServiceImpl es=new UserDetailsServiceImpl();
//    	 
//    	 es.loadUserByUsername("shubham");
//    	 
//    	 
//    	 assertThat((es.loadUserByUsername("shubham")).equals(savedUser));
//    	 
//      }
        
    }

