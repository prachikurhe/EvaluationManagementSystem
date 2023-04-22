package com.yash.ems.services;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.yash.ems.model.Employee;
import com.yash.ems.model.EmployeeFeedback;
import com.yash.ems.model.Skill;
import com.yash.ems.model.User;

public interface FeedbackService {
	
	public List<Skill> getSkills();
	
	public List<Employee> getEmployees();
	
	public List<Employee> getFeedbackEmployees();

	public List<EmployeeFeedback> getAllEmployeeFedback();

	public List<EmployeeFeedback> getEmployeeFedbacksByEmployeeId(int id);
	
	public EmployeeFeedback saveEmployeeFeedback(EmployeeFeedback employeeFeedback);
	
	public List<String> uploadEmployeeFeedback(MultipartFile file, User createdBy)throws IOException;
	
	public XSSFWorkbook downloadEmployeeFeedbackTemplate();
}
