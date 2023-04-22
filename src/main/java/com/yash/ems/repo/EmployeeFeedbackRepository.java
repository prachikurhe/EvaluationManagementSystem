package com.yash.ems.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yash.ems.model.EmployeeFeedback;

@Repository
public interface EmployeeFeedbackRepository extends JpaRepository<EmployeeFeedback, Integer> {

	List<EmployeeFeedback> findAllByOrderByIdDesc();
	
	@Query(value = "SELECT * FROM employee_feedback WHERE employee_id = ?1", nativeQuery = true)
	public List<EmployeeFeedback> getEmployeeFedbacksByEmployeeId(int employeeId);
}
