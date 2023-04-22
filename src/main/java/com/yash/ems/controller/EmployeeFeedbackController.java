package com.yash.ems.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.yash.ems.config.LoggerConfiguration;
import com.yash.ems.model.Employee;
import com.yash.ems.model.EmployeeFeedback;
import com.yash.ems.model.Skill;
import com.yash.ems.model.User;
import com.yash.ems.services.FeedbackService;

import io.swagger.annotations.ApiOperation;

/**
 * employee_feedback_controller interact with service layer to complete the work
 * according to web request.
 *
 * @author Mohammad Sadav Khan and Amol Sadashiv Bhosale
 * @since 01-03-2023
 */
@RestController
@RequestMapping("/feedback/api")
@CrossOrigin("*")
public class EmployeeFeedbackController {
	
	private Logger logger = LoggerConfiguration.getLogger(EmployeeFeedbackController.class);

	@Autowired
	private FeedbackService feedbackService;
	
	/**
	 * This controller method handles the Get request to access list of all
	 * employee.
	 */
	@ApiOperation(value = "fetch all employee.")
	@GetMapping("/allEmployee")
    public List<Employee> getAllEmployee() {
		
		String methodName = "getAllEmployee()";
		logger.info(methodName + " called"); 

		return feedbackService.getEmployees();
    }
	
	/**
	 * This controller method handles the Get request to access list of all
	 * skill.
	 */
	@ApiOperation(value = "fetch all skill.")
	@GetMapping("/allSkill")
    public List<Skill> getAllSkill() {
		
		String methodName = "getAllSkill()";
		logger.info(methodName + " called"); 

		return feedbackService.getSkills();
    }
	
	/**
	 * This controller method handles the Get request to access list of all
	 * employee which is having feedback.
	 */
	@ApiOperation(value = "fetch all employee which is having feedback.")
	@GetMapping("/getFeedbackEmployees")
    public List<Employee> getFeedbackEmployees() {

		String methodName = "getFeedbackEmployees()";
		logger.info(methodName + " called"); 
		
		return feedbackService.getFeedbackEmployees();
    }
	
	/**
	 * This controller method handles the Get request to access list of all
	 * employee feedback.
	 */
	@ApiOperation(value = "fetch all employee feedback.")
	@GetMapping("/allEmployeeFeedback")
    public List<EmployeeFeedback> getAllEmployeeFedback() {

		String methodName = "getAllEmployeeFedback()";
		logger.info(methodName + " called"); 
		
		return feedbackService.getAllEmployeeFedback();
    }
	
	/**
	 * This controller method handles the Get request to access list of all
	 * employee feedback based on employee id.
	 */
	@ApiOperation(value = "fetch employee-feedback based on employee id.")
	@GetMapping("/getEmployeeFedbacksByEmployeeId/{employeeId}")
    public List<EmployeeFeedback> getEmployeeFedbacksByEmployeeId(@PathVariable(value = "employeeId") Integer employeeId) {

		String methodName = "getEmployeeFedbacksByEmployeeId()";
		logger.info(methodName + " called"); 
		
		return feedbackService.getEmployeeFedbacksByEmployeeId(employeeId);
    }	
	
	/**
	 * This controller method handles the HTTP Post request for insert employee feedback,
	 * matching with the given URI.
	 * 
	 * @param employeeFeedback
	 */
	@ApiOperation(value = "Insert employee feedback.")
	@PostMapping("/saveEmployeeFeedback")
	public ResponseEntity<EmployeeFeedback> savEmployeeFeedback(@RequestBody EmployeeFeedback employeeFeedback) 
			throws SQLException, Exception {
		
		String methodName = "savEmployeeFeedback()";
		logger.info(methodName + " called"); 

		EmployeeFeedback ef = feedbackService.saveEmployeeFeedback(employeeFeedback);

		return ResponseEntity.status(HttpStatus.CREATED).body(ef);
	}
	
	/**
	 * This controller method handles the HTTP Post request for upload employee feedback,
	 * matching with the given URI.
	 * 
	 * @param file
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@ApiOperation(value = "upload employee feedback data")
	@PostMapping("/upload-employee-feedback")
	public List<String> uploadEmployeeFeedback(@RequestParam("file") MultipartFile file, @RequestParam String createdBy)
			throws JsonParseException, JsonMappingException, IOException,
			SQLException  {

		String methodName = "uploadEmployeeFeedback()";
		logger.info(methodName + " called");

		User uploadedByUser = new User();
		uploadedByUser.setId(new Long(createdBy.toString()));
		
		return feedbackService.uploadEmployeeFeedback(file, uploadedByUser);
    }
	
	/**
	 * This controller method handles the HTTP Get request for download employee feedback template,
	 * matching with the given URI.
	 */
	@GetMapping(value = "/download-employee-feedback-template")
	public ResponseEntity<ByteArrayResource> downloadEmployeeFeedbackTemplate() throws Exception {
		
		String methodName = "downloadEmployeeFeedbackTemplate()";
		logger.info(methodName + " called");
		
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			
			XSSFWorkbook workbook = feedbackService.downloadEmployeeFeedbackTemplate();
			
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "force-download"));
			header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = employeeFeedbackTemplate.xlsx");
			workbook.write(stream);
			stream.close();
			
			return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
					header, HttpStatus.CREATED);
			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}