package com.yash.ems.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yash.ems.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	public List<Employee>findBycurrentAllocationContaining(String currentAllocation);
	
	public Employee findByEmployeeIdAndEmployeeName(Integer employeeId, String employeeName);
	
	@Query(value = "SELECT * FROM employee WHERE employee_name = ?1 AND emp_id = ?2", nativeQuery = true)
	List<Employee> getEmployeesByNameAndId(String employeeName, String employeeId);
	
	@Query(value = "SELECT * FROM employee WHERE id IN (SELECT employee_id FROM employee_feedback)", nativeQuery = true)
	List<Employee> getFeedbackEmployees();
}