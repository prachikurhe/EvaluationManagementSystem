package com.yash.ems.controller;



import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.mock.web.MockHttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.yash.ems.model.Employee;
import com.yash.ems.model.EmployeeFeedback;
import com.yash.ems.model.EmployeeFeedbackFile;
import com.yash.ems.model.EmployeeFile;
import com.yash.ems.model.EmployeeSkillsRating;
import com.yash.ems.model.Role;
import com.yash.ems.model.Skill;
import com.yash.ems.model.User;
import com.yash.ems.model.UserRole;
import com.yash.ems.services.FeedbackService;




@WebMvcTest(value=EmployeeFeedbackController.class)
public class FeedbackControllerTests {
	
	@MockBean
	private FeedbackService feedbackService;
	
	@Autowired 
	  private MockMvc mockMvc;
	
	LocalDateTime date = LocalDateTime.now(); 
	
	@Test
	@Order(1)
	public void test_getAllskills() throws Exception {
		List<Skill> skills=new ArrayList<>();
		skills.add(new Skill(1, "java"));
		skills.add(new Skill(2, "mysql"));
		skills.add(new Skill(3, "SpringBoot"));
		skills.add(new Skill(4, "Microservices"));
	    skills.add(new Skill(5, "Angular"));
		
		when(feedbackService.getSkills()).thenReturn(skills);
		MockHttpServletRequestBuilder ReqBuilder = MockMvcRequestBuilders.get("/feedback/api/allSkill");
		
		ResultActions perform = mockMvc.perform(ReqBuilder);
		MvcResult mvcReturn = perform.andReturn();
		MockHttpServletResponse response = mvcReturn.getResponse();
		int status = response.getStatus();
		assertEquals(200,status);
		
	}
	
	@Test
	@Order(2)
	public void test_getAllEmployees() throws Exception {
		List<Skill> skills=new ArrayList<>();
		skills.add(new Skill(1, "java"));
		skills.add(new Skill(2, "mysql"));
		skills.add(new Skill(3, "SpringBoot"));
		skills.add(new Skill(4, "Microservices"));
	    skills.add(new Skill(5, "Angular"));
		
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
	   		role.setRoleName("ADMIN");
	   		
	   	 Set<UserRole> roles = new HashSet<>();
	   		
	   		UserRole userRole = new UserRole();
	        
	        userRole.setUser(user);
	        userRole.setRole(role);

	        roles.add(userRole);
		
		EmployeeFile employeeFile = new EmployeeFile(3, "BG4-BU5-Associates (1).xlsx",LocalDateTime.now() , user);
		
		List<Employee> employee=new ArrayList<>();
		
		employee.add(new Employee(1,101,"Amol","amol@gmail.com","Software Engg.", "E3", "", "2022-06-16", 3.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", employeeFile,user,skills));
		employee.add(new Employee(2,101, "pratik","pratik@gmail.com","Software Engg.", "E3", "", "2022-09-10", 4.0, "maruti","Magarpatta", "Current Allocation", "Project", employeeFile,user,skills));
		employee.add(new Employee(3,101, "rahul","rahul@gmail.com","Software Engg.", "E3", "", "2022-03-03", 5.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", employeeFile,user,skills));
		employee.add(new Employee(4,101, "sumit","sumit@gmail.com","Software Engg.", "E3", "", "2023-01-22", 6.0, "maruti","Magarpatta", "Current Allocation", "Project", employeeFile,user,skills));
		
		
		when(feedbackService.getEmployees()).thenReturn(employee);
		MockHttpServletRequestBuilder ReqBuilder = MockMvcRequestBuilders.get("/feedback/api/allEmployee");
		
		ResultActions perform = mockMvc.perform(ReqBuilder);
		MvcResult mvcReturn = perform.andReturn();
		MockHttpServletResponse response = mvcReturn.getResponse();
		int status = response.getStatus();
		assertEquals(200,status);
		
	}
	
	@Test
	@Order(3)
	public void test_getAllEmployeeFeedback() throws Exception {
		List<Skill> skills=new ArrayList<>();
		skills.add(new Skill(1, "java"));
		skills.add(new Skill(2, "mysql"));
		skills.add(new Skill(3, "SpringBoot"));
		skills.add(new Skill(4, "Microservices"));
		skills.add(new Skill(5, "Angular"));
		
		
		List<EmployeeFeedback> empFeedbacks =new ArrayList<EmployeeFeedback>();
		
		List<EmployeeSkillsRating> skillList= new ArrayList<>();
		skillList.add(new EmployeeSkillsRating (1,"2 - Below Average","good",new Skill(1,"java")));
		
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
	   		role.setRoleName("ADMIN");
	   		
	   	 Set<UserRole> roles = new HashSet<>();
	   		
	   		UserRole userRole = new UserRole();
	        
	        userRole.setUser(user);
	        userRole.setRole(role);

	        roles.add(userRole);
		EmployeeFile employeeFile = new EmployeeFile(3, "BG4-BU5-Associates (1).xlsx",LocalDateTime.now() , user);
		empFeedbacks.add(new EmployeeFeedback(2,3,1,"comment","suggestion",LocalDateTime.now(),new Employee(1,101, "Amol","amol@gmail.com","Software Engg.", "E3", "", "2022-06-16", 3.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", employeeFile,user,skills),new EmployeeFeedbackFile(1,"filename",LocalDateTime.now(),user),user, skillList));
		empFeedbacks.add(new EmployeeFeedback(2,3,1,"comment","suggestion",LocalDateTime.now(),new Employee(1,101, "Amol","amol@gmail.com","Software Engg.", "E3", "", "2022-06-16", 3.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", employeeFile,user,skills),new EmployeeFeedbackFile(1,"filename",LocalDateTime.now(),user),user, skillList));
		
		when(feedbackService.getAllEmployeeFedback()).thenReturn(empFeedbacks);
		MockHttpServletRequestBuilder ReqBuilder = MockMvcRequestBuilders.get("/feedback/api/allEmployeeFeedback");
		
		ResultActions perform = mockMvc.perform(ReqBuilder);
		MvcResult mvcReturn = perform.andReturn();
		MockHttpServletResponse response = mvcReturn.getResponse();
		int status = response.getStatus();
		assertEquals(200,status);
		
	}
	

	 @Test 
	 @Order(4)
	 public void testSaveEmployeeFeedback() throws JsonProcessingException, Exception {
		 List<Skill> skills =new ArrayList<>();
			skills.add(new Skill(1, "java"));
			skills.add(new Skill(2, "mysql"));
			skills.add(new Skill(3, "SpringBoot"));
			skills.add(new Skill(4, "Microservices"));
			skills.add(new Skill(5, "Angular"));

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
		   		role.setRoleName("ADMIN");
		   		
		   	 Set<UserRole> roles = new HashSet<>();
		   		
		   		UserRole userRole = new UserRole();
		        
		        userRole.setUser(user);
		        userRole.setRole(role);

		        roles.add(userRole);
			
			List<EmployeeSkillsRating> skillList= new ArrayList<>();
			skillList.add(new EmployeeSkillsRating (1,"2 - Below Average","good",new Skill(1,"java")));
			skillList.add(new EmployeeSkillsRating (2,"5 - Excellent","very good",new Skill(2,"HTML")));
			EmployeeFile employeeFile = new EmployeeFile(3, "BG4-BU5-Associates (1).xlsx",LocalDateTime.now() ,user);
	       
			EmployeeFeedback employeefeedback=new  EmployeeFeedback(1,6,5,"aaaa","suggestion",LocalDateTime.now(),new Employee(1,101, "Amol","amol@gmail.com","Software Engg.", "E3", "", "2022-06-16", 3.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", employeeFile,user,skills),new EmployeeFeedbackFile(1,"filename",LocalDateTime.now(),user),user, skillList);
			
			System.out.println(employeefeedback);
			when(feedbackService.saveEmployeeFeedback(employeefeedback)).thenReturn(employeefeedback);
			
			ObjectMapper mapper= new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			String feedbackJason = mapper.writeValueAsString(employeefeedback);
			System.out.println(feedbackJason);
			
			 MockHttpServletRequestBuilder ReqBuilder = MockMvcRequestBuilders.post("/feedback/api/saveEmployeeFeedback")
						.contentType(MediaType.APPLICATION_JSON)
						.content(feedbackJason);
			 
			 ResultActions perform = mockMvc.perform(ReqBuilder);
				MvcResult mvcReturn = perform.andReturn();
				MockHttpServletResponse response = mvcReturn.getResponse();
				int status = response.getStatus();
				assertEquals(201,status);
	  
	     
	 }

}
