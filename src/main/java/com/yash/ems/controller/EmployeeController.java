package com.yash.ems.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.yash.ems.model.Employee;
import com.yash.ems.model.Skill;
import com.yash.ems.model.User;
import com.yash.ems.service.impl.EmployeeService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	
	@GetMapping("/getSkillData")
	public ResponseEntity<List<Skill>> getAllSkillData(){
		LOGGER.info("In Employee Controller....get all skill data ");
		List<Skill> skillNameContain = employeeService.getAllSkill();
		return new ResponseEntity<>(skillNameContain,HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/addEmployeeData")
	public ResponseEntity<Employee> addEmployeeObject(@Valid @RequestBody Employee employee) {
		LOGGER.info("In Employee Controller....Save Employee data ");
		
		User createdBy = new User();
		createdBy.setId(1L);
		
		employee.setCreatedBy(createdBy);
		
		Employee saveEmployeeObject = employeeService.saveEmployeeObject(employee);
		return new ResponseEntity<>(saveEmployeeObject, HttpStatus.CREATED);
	}

	@GetMapping(value = "/getAllEmployeeData")
	public ResponseEntity<List<Employee>> getAllEmployeeData() {
		LOGGER.info("In Employee Controller....get Employee data ");
		List<Employee> allEmployeeObject = employeeService.getAllEmployeeObject();
		return new ResponseEntity<>(allEmployeeObject, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getEmployeeData/{employeeId}")
	public ResponseEntity<Employee> getSingleEmployeeData(@PathVariable int employeeId) {
		LOGGER.info("In Employee Controller....get single Employee data ");
		Employee singleEmployeeData = employeeService.getSingleEmployeeData(employeeId);
		return ResponseEntity.ok(singleEmployeeData);
	}

	@PutMapping(value = "/updateEmployee/{employeeId}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee) {
		LOGGER.info("In Employee Controller....update Employee data ");
		Employee updateEmployeeObject = employeeService.updateEmployeeObject(employeeId, employee);
		return new ResponseEntity<>(updateEmployeeObject, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/deleteEmployee/{employeeId}")
	public ResponseEntity<Map<String, Integer>> deleteEmployee(@PathVariable int employeeId) {
		LOGGER.info("In Employee Controller....delete Employee data ");
		Integer eid2 = employeeService.deleteEmployeeObject(employeeId);
		Map<String, Integer> map = new HashMap<>();
		map.put("deleted id is", eid2);
		return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{currentAllocation}")
	public List<Employee> getEmployeeBycurrentAllocation(@PathVariable("currentAllocation") String currentAllocation) {
		LOGGER.info("In Employee Controller....fetch Employee data by current allocation ");
		System.out.println("----------------------" + currentAllocation);
		List<Employee> BycurrentAllocation = this.employeeService.fetchBycurrentAllocation(currentAllocation);
		System.out.println("bycurrentAllocation :" + BycurrentAllocation);
		return BycurrentAllocation;

	}

	@GetMapping("/find-by-id-name/{employeeId}/{employeeName}")
	public ResponseEntity<Employee> findByEmployeeIdAndEmployeeName(
			@PathVariable(value = "employeeId") Integer employeeId,
			@PathVariable(value = "employeeName") String employeeName) {
		LOGGER.info("In Employee Controller....fine Employee data by its name and id");
		Employee employee = employeeService.fetchByEmployeeIdAndEmployeeName(employeeId, employeeName);
		return new ResponseEntity<>(employee, HttpStatus.OK);

	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "upload employee data")
	@PostMapping("/upload-employee-data")
	public List<String> uploadEmployeeData(@RequestParam("file") MultipartFile file)
			throws JsonParseException, JsonMappingException, IOException,
			SQLException  {
		LOGGER.info("In Employee Controller....upload employee data..");
		
		User createdBy = new User();
		createdBy.setId(1L);
		
		return employeeService.uploadEmployeeFile(file, createdBy);
    }

	@GetMapping(value = "/download-employee-template") 
	public ResponseEntity<ByteArrayResource> downloadEmployeeTemplate() throws Exception {
	  
	  String methodName = "downloadEmployeeTemplate()"; 
	  LOGGER.info(methodName +" called");
	  
	  try { ByteArrayOutputStream stream = new ByteArrayOutputStream();
	  
	  XSSFWorkbook workbook = employeeService.downloadEmployeeTemplate();
	  
	  HttpHeaders header = new HttpHeaders();
	  header.setContentType(new MediaType("application", "force-download"));
	  header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = employeeTemplate.xlsx");
	  workbook.write(stream); stream.close();
	  
	  return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
	  header, HttpStatus.CREATED);
	  
	  }catch (Exception e) { return new
	  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); } }
}