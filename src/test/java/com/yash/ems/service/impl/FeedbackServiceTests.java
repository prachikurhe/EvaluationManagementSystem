package com.yash.ems.service.impl;




import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.yash.ems.model.Employee;
import com.yash.ems.model.EmployeeFeedback;
import com.yash.ems.model.EmployeeFeedbackFile;
import com.yash.ems.model.EmployeeFile;
import com.yash.ems.model.EmployeeSkillsRating;
import com.yash.ems.model.Role;
import com.yash.ems.model.Skill;
import com.yash.ems.model.User;
import com.yash.ems.model.UserRole;
import com.yash.ems.repo.EmployeeFeedbackRepository;
import com.yash.ems.repo.EmployeeRepository;
import com.yash.ems.repo.SkillRepository;


@SpringBootTest (classes= {FeedbackServiceTests.class})
public class FeedbackServiceTests {
	
	@Mock
	EmployeeFeedbackRepository empFeedRepo;
	
	@Mock
	SkillRepository skillRepo;
	
	
	@Mock
	EmployeeRepository empRepo;
	
	
	
	@InjectMocks
	FeedbackServiceImpl feedbackService ;

	public List<EmployeeFeedback> allFeedbacks;
	public List<Skill> skills;
	
	
	// JUnit test for Get All Skills REST API
	@Test
	@Order(1)
	public void getAllSkill() {
		
		List<Skill> skills=new ArrayList<>();
		skills.add(new Skill(1, "java"));
		skills.add(new Skill(2, "mysql"));
		skills.add(new Skill(3, "SpringBoot"));
		skills.add(new Skill(4, "Microservices"));
	    skills.add(new Skill(5, "Angular"));
		
		when(skillRepo.findAll()).thenReturn(skills);
		
		int expectedResult=5;
		//positive scenario
		assertEquals(expectedResult,feedbackService.getSkills().size());
		
		
		
		//Negative scenario
	    // assertEquals(4,feedbackService.getSkills().size();
		
		
		
		
		
	}
	
	
	// JUnit test for Get All Employees REST API
		@Test
		@Order(2)
		public void getAllEmployee() {
			
			Role role= new Role(Long.valueOf(1),"roleName");

			Set<UserRole> userRole= new HashSet<UserRole>();
			

			User user = new User(Long.valueOf(1),"username","password","firstName","Lastname","user@email.com","99999999",true,"Project Manager", userRole);
			
			List<Employee> employee=new ArrayList<>();
			
			employee.add(new Employee(1,101,"Amol","amol@gmail.com","Software Engg.", "E3", "", "2022-06-16", 3.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", null,user,skills));
			employee.add(new Employee(2,101, "pratik","pratik@gmail.com","Software Engg.", "E3", "", "2022-09-10", 4.0, "maruti","Magarpatta", "Current Allocation", "Project", null,user,skills));
			employee.add(new Employee(3,101, "rahul","rahul@gmail.com","Software Engg.", "E3", "", "2022-03-03", 5.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", null,user,skills));
			employee.add(new Employee(4,101, "sumit","sumit@gmail.com","Software Engg.", "E3", "", "2023-01-22", 6.0, "maruti","Magarpatta", "Current Allocation", "Project", null,user,skills));
			
			
			when(empRepo.findAll()).thenReturn(employee);
			
			assertEquals(4,feedbackService.getEmployees().size());
			
			//assertTrue(feedbackService.getEmployees().contains(new Employee(1,101,"Amol","amol@gmail.com","Software Engg.", "E3", "", "2022-06-16", 3.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", null,new User(1,"2","3","4","5"),skills)));
	
		}
	
		
		
		
		
	@Test
	@Order(3)
	public void test_getAllEmployeeFeedback() {
		
		List<Skill> skills=new ArrayList<>();
		skills.add(new Skill(1, "java"));
		skills.add(new Skill(2, "mysql"));
		skills.add(new Skill(3, "SpringBoot"));
		skills.add(new Skill(4, "Microservices"));
		skills.add(new Skill(5, "Angular"));
		
		Role role= new Role(Long.valueOf(1),"roleName");

		Set<UserRole> userRole= new HashSet<UserRole>();
		

		User user = new User(Long.valueOf(1),"username","password","firstName","Lastname","user@email.com","99999999",true,"Project Manager", userRole);
		
		
		List<EmployeeFeedback> empFeedbacks=new ArrayList<EmployeeFeedback>();
		
		List<EmployeeSkillsRating> skillList= new ArrayList<>();
		skillList.add(new EmployeeSkillsRating (1,"2 - Below Average","good",new Skill(1,"java")));
		
		EmployeeFile employeeFile = new EmployeeFile(3, "BG4-BU5-Associates (1).xlsx",LocalDateTime.now() , user);
		
		empFeedbacks.add(new EmployeeFeedback(1,6,5,"aaaa","suggestion",LocalDateTime.now(),new Employee(1,101, "Amol","amol@gmail.com","Software Engg.", "E3", "", "2022-06-16", 3.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", employeeFile,user,skills),new EmployeeFeedbackFile(1,"filename",LocalDateTime.now(),user),user, skillList));
		empFeedbacks.add(new EmployeeFeedback(2,3,1,"comment","suggestion",LocalDateTime.now(),new Employee(1,101, "Amol","amol@gmail.com","Software Engg.", "E3", "", "2022-06-16", 3.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", employeeFile,user,skills),new EmployeeFeedbackFile(1,"filename",LocalDateTime.now(),user),user, skillList));
		
		when(empFeedRepo.findAll()).thenReturn(empFeedbacks);
	    
		assertEquals(2,feedbackService.getAllEmployeeFedback().size());
	}
	
	@Test
	@Order(4)
	public void saveEmployeeFeedback() {
		List<Skill> skills=new ArrayList<>();
		skills.add(new Skill(1, "java"));
		skills.add(new Skill(2, "mysql"));
		skills.add(new Skill(3, "SpringBoot"));
		skills.add(new Skill(4, "Microservices"));
		skills.add(new Skill(5, "Angular"));
		
		List<EmployeeSkillsRating> skillList= new ArrayList<>();
		skillList.add(new EmployeeSkillsRating (1,"2 - Below Average","good",new Skill(1,"java")));
		skillList.add(new EmployeeSkillsRating (2,"5 - Excellent","very good",new Skill(2,"HTML")));
		
		Role role= new Role(Long.valueOf(1),"roleName");

		Set<UserRole> userRole= new HashSet<UserRole>();
		
		User user = new User(Long.valueOf(1),"username","password","firstName","Lastname","user@email.com","99999999",true,"Project Manager", userRole);
		
		EmployeeFile employeeFile = new EmployeeFile(3, "BG4-BU5-Associates (1).xlsx",LocalDateTime.now() , user);
        
		EmployeeFeedback employeefeedback=new EmployeeFeedback(1,6,3,"comment","learn more technology",LocalDateTime.now(),new Employee(1,101, "Amol","amol@gmail.com","Software Engg.", "E3", "", "2022-06-16", 3.0, "Yogeshwar","Hinjewadi", "Current Allocation", "Project", employeeFile,user,skills),new EmployeeFeedbackFile(1,"filename",LocalDateTime.now(),user),user, skillList);
		
		
		when(empFeedRepo.save(employeefeedback)).thenReturn(employeefeedback);
		
		
		assertEquals(employeefeedback,feedbackService.saveEmployeeFeedback(employeefeedback));
		
		assertEquals(6,feedbackService.saveEmployeeFeedback(employeefeedback).getOverallExperience());
		
		
		
		assertEquals("learn more technology",feedbackService.saveEmployeeFeedback(employeefeedback).getSuggestion());
	
		assertEquals("Amol",feedbackService.saveEmployeeFeedback(employeefeedback).getEmployee().getEmployeeName());
	}
}
