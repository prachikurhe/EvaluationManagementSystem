package com.yash.ems.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.yash.ems.model.Employee;
import com.yash.ems.model.EmployeeFile;
import com.yash.ems.model.Role;
import com.yash.ems.model.Skill;
import com.yash.ems.model.User;
import com.yash.ems.model.UserRole;
import com.yash.ems.repo.EmployeeRepository;
import com.yash.ems.service.impl.EmployeeService;



@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false) 
public class CreateOnboardModuleTest {
	
	@Mock 
	EmployeeRepository employeeRepository;
	  
	  @InjectMocks 
	  EmployeeService employeeService;
	  
	  LocalDateTime date = LocalDateTime.now();
	  
	  @Test 
	  void testSaveEmployeeData() {
	  
		    Role role= new Role(Long.valueOf(1),"roleName");

			Set<UserRole> userRole= new HashSet<UserRole>();
			

			User user = new User(Long.valueOf(1),"username","password","firstName","Lastname","user@email.com","99999999",true,"Project Manager", userRole);
	  
	  EmployeeFile employeeFile = new EmployeeFile(3,"BG4-BU5-Associates (1).xlsx",date , user);
	  
	  List<Skill> skills=Arrays.asList(new Skill(1,"java"), new Skill(2,"MySql"));
	  Employee employee=new Employee(1, 101, "Aaarti", "aarti@gmail.com", "software engineer", "E1", "Trainee", "12-10-2022", 2.3, "Mr.Patil", "BasicPune-Hinjewadi III-DC", "javashared", "Internal EMS", employeeFile, user, skills);
	  
	 
	  when(employeeRepository.save(employee)).thenReturn(employee); Employee
	  saveEmployee = employeeService.saveEmployeeObject(employee);
	  assertThat(saveEmployee.getEmployeeName()).isSameAs(employee.getEmployeeName(
	  ));
	  //assertThat(saveEmployee.employeeName()).isSameAs(employee.employeeName());
	  //verify(employeeRepository).save(employee);
	  Mockito.verify(employeeRepository, Mockito.atLeastOnce()).save(employee);
	  }
	  
	  @Test 
	  void testAllEmployeeList() {
		  
		  List<Employee>list=new ArrayList<>(); 
		  list.add(new Employee());
		  when(employeeRepository.findAll()).thenReturn(list);
		  List<Employee> allEmployeeObject = employeeService.getAllEmployeeObject();
		  assertEquals(list, allEmployeeObject);
		  Mockito.verify(employeeRepository).findAll();
		  
	  }
	  
	  @Test 
	  void testDeleteEmployeeObject() { 
		  Role role= new Role(Long.valueOf(1),"roleName");

			Set<UserRole> userRole= new HashSet<UserRole>();
			

			User user = new User(Long.valueOf(1),"username","password","firstName","Lastname","user@email.com","99999999",true,"Project Manager", userRole);
	  
	  EmployeeFile employeeFile = new EmployeeFile(3,"BG4-BU5-Associates (1).xlsx",date , user);
	  
	  List<Skill> skills=Arrays.asList(new Skill(1,"java"), new Skill(2,"MySql"));
	  Employee employee=new Employee(1, 101, "Aaarti", "aarti@gmail.com", "software engineer", "E1", "Trainee", "12-10-2022", 2.3, "Mr.Patil", "BasicPune-Hinjewadi III-DC", "javashared", "Internal EMS", employeeFile, user, skills);
	  
	  when(employeeRepository.findById(employee.getEmployeeId())).thenReturn(
	  Optional.of(employee));
	  employeeService.getSingleEmployeeData(employee.getEmployeeId());
	  Mockito.verify(employeeRepository,
	  Mockito.atLeastOnce()).findById(employee.getEmployeeId());
	  
	  }
	  
	  @Test 
	  void testUpdateEmployeeObject() { 
		Role role= new Role(Long.valueOf(1),"roleName");
		Set<UserRole> userRole= new HashSet<UserRole>();
		User user = new User(Long.valueOf(1),"username","password","firstName","Lastname","user@email.com","99999999",true,"Project Manager", userRole);
	    EmployeeFile employeeFile = new EmployeeFile(3,"BG4-BU5-Associates (1).xlsx",date , user);
	    List<Skill> skills=Arrays.asList(new Skill(1,"java"), new Skill(2,"MySql"));
	    Employee employee=new Employee(1, 101, "Aaarti", "aarti@gmail.com", "software engineer", "E1", "Trainee", "12-10-2022", 2.3, "Mr.Patil", "BasicPune-Hinjewadi III-DC", "javashared", "Internal EMS", employeeFile, user, skills);
	  
	    
	    Role role1= new Role(Long.valueOf(1),"roleName");
		Set<UserRole> userRole1= new HashSet<UserRole>();
		User user1 = new User(Long.valueOf(1),"username","password","firstName","Lastname","user@email.com","99999999",true,"Project Manager", userRole);
        EmployeeFile employeeFile1 = new EmployeeFile(3,"BG4-BU5-Associates (1).xlsx",date , user);
        List<Skill> skills1=Arrays.asList(new Skill(1,"angular"), new Skill(2,"MySql"));
        Employee employee1=new Employee(1, 101, "Kirti", "aarti@gmail.com", "software engineer", "E1", "Trainee", "12-10-2022", 2.3, "Mr.Patil", "BasicPune-Hinjewadi III-DC", "javashared", "Internal EMS", employeeFile, user, skills);
	 
        when(employeeRepository.findById(employee.getEmployeeId())).thenReturn( Optional.of(employee));
	    employeeService.updateEmployeeObject(employee.getEmployeeId(), employee1);
	    Mockito.verify(employeeRepository, Mockito.atLeastOnce()).save(any());
	    }
	  
		  

}
