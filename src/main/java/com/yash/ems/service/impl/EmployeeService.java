package com.yash.ems.service.impl;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yash.ems.exception.ResourceNotFoundException;
import com.yash.ems.model.Employee;
import com.yash.ems.model.EmployeeFile;
import com.yash.ems.model.Skill;
import com.yash.ems.model.User;
import com.yash.ems.repo.EmployeeFileRepository;
import com.yash.ems.repo.EmployeeRepository;
import com.yash.ems.repo.SkillRepository;



@Service
public class EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	SkillRepository skillRepository;

	@Autowired
	private Environment env;
	
	@Autowired
	EmployeeFileRepository employeeFileRepository;

	String message = "Employee is not exist with this id ";
	
	public List<Skill> getAllSkill() {
		
		return skillRepository.findAll();
	}

	public Employee saveEmployeeObject(Employee employee) {
		LOGGER.info("In Employee Service....Save Employee data ");

		return employeeRepository.save(employee);
	}

	public List<Employee> getAllEmployeeObject() {
		LOGGER.info("In Employee Service....get all Employee data ");
		return employeeRepository.findAll();

	}

	public Employee getSingleEmployeeData(int employeeId) {
		LOGGER.info("In Employee Service....get single Employee data ");
		return employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(message + employeeId));
	}

	public Employee updateEmployeeObject(int employeeId, Employee employeeDetails) {
		LOGGER.info("In Employee Service....update single Employee data ");
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(message + employeeId));
		employee.setEmployeeName(employeeDetails.getEmployeeName());
		employee.setEmail(employeeDetails.getEmail());
		employee.setDesignation(employeeDetails.getDesignation());
		employee.setGrade(employeeDetails.getGrade());
		employee.setResourceType(employeeDetails.getResourceType());
		employee.setDOJ(employeeDetails.getDOJ());
		employee.setTotalExp(employeeDetails.getTotalExp());
		employee.setIRM(employeeDetails.getIRM());
		employee.setCurrentLocation(employeeDetails.getCurrentLocation());
		employee.setCurrentAllocation(employeeDetails.getCurrentAllocation());
		employee.setProject(employeeDetails.getProject());
		employee.setSkills(employeeDetails.getSkills());
		employeeRepository.save(employee);
		return employee;
	}

	public Integer deleteEmployeeObject(int employeeId) {
		LOGGER.info("In Employee Service....delete Employee data ");

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(message + employeeId));
		int eid2 = employee.getEmployeeId();
		employeeRepository.delete(employee);
		return eid2;
	}

	public List<Employee> fetchBycurrentAllocation(String currentAllocation) {
		LOGGER.info("In Employee Service....fetch Employee data by current allocation ");

		List<Employee> currentAllocationContaining = employeeRepository
				.findBycurrentAllocationContaining(currentAllocation);
		return currentAllocationContaining;
	}

	public Employee fetchByEmployeeIdAndEmployeeName(Integer employeeId, String employeeName) {
		LOGGER.info("In Employee Service....fetch Employee data by employee id and employee name ");

		return employeeRepository.findByEmployeeIdAndEmployeeName(employeeId, employeeName);
	}

	/*
	 * public List<String> uploadEmployeeFile(MultipartFile file, User
	 * createdBy)throws IOException {
	 * 
	 * String methodName = "uploadEmployeeFile()"; LOGGER.info(methodName +
	 * " called");
	 * 
	 * List<String> errorList = new ArrayList<String>();
	 * 
	 * List<Employee> employee = new ArrayList<>();
	 * 
	 * if(file != null) {
	 * 
	 * try {
	 * 
	 * String filetype =
	 * file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(
	 * ".") + 1, file.getOriginalFilename().length());
	 * 
	 * if(filetype.equalsIgnoreCase("xlsx")) { List<Skill> skills =
	 * skillRepository.findAll();
	 * 
	 * XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	 * 
	 * int startRow = 2; XSSFRow inputRow = null; XSSFCell cell = null;
	 * 
	 * if(workbook != null && workbook.getSheetAt(0) != null) {
	 * 
	 * XSSFSheet sheet = workbook.getSheetAt(0);
	 * 
	 * int totalRows = sheet.getPhysicalNumberOfRows(); int noOfColumns =
	 * sheet.getRow(1).getPhysicalNumberOfCells();
	 * 
	 * for(int i = startRow; i < totalRows; i++) {
	 * 
	 * inputRow = sheet.getRow(i);
	 * 
	 * if(inputRow == null) continue;
	 * 
	 * Employee ef = new Employee();
	 * 
	 * int cid = 1;
	 * 
	 * 
	 * for(int j = 1; j < noOfColumns; j++) {
	 * 
	 * cell = inputRow.getCell(j);
	 * 
	 * 
	 * if(cid == 1) {
	 * 
	 * 
	 * if(cell != null) {
	 * 
	 * if(cell.getCellType() == CellType.NUMERIC) {
	 * ef.setEmployeeId((int)cell.getNumericCellValue()); } else {
	 * errorList.add("Enter employee id in numeric integer format in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1)); }
	 * 
	 * } else { errorList.add("Overall Experience code is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } } else if(cid == 2) { if(cell != null) {
	 * if(cell.getCellType() == CellType.STRING) {
	 * ef.setEmployeeName(cell.getStringCellValue()); } else {
	 * errorList.add("Employee name is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * else if(cid == 3) { if(cell != null) { if(cell.getCellType() ==
	 * CellType.STRING) { ef.setEmail(cell.getStringCellValue()); } else {
	 * errorList.add("Employee empty is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * else if(cid == 4) { if(cell != null) { if(cell.getCellType() ==
	 * CellType.STRING) { ef.setDesignation(cell.getStringCellValue()); } else {
	 * errorList.add("Employee designation is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * else if(cid == 5) { if(cell != null) { if(cell.getCellType() ==
	 * CellType.STRING) { ef.setGrade(cell.getStringCellValue()); } else {
	 * errorList.add("Employee designation is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * else if(cid == 6) { if(cell != null) { if(cell.getCellType() ==
	 * CellType.STRING) { ef.setResourceType(cell.getStringCellValue()); } else {
	 * errorList.add("Employee resource type is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * else if(cid == 7) { if(cell != null) { if(cell.getCellType() ==
	 * CellType.STRING) { ef.setDOJ(cell.getStringCellValue()); } else {
	 * errorList.add("Employee date of joining is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * else if(cid == 8) { if(cell != null) { if(cell.getCellType() ==
	 * CellType.NUMERIC) { ef.setTotalExp((double)cell.getNumericCellValue()); }
	 * else { errorList.add("Employee total experience is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * else if(cid == 9) { if(cell != null) { if(cell.getCellType() ==
	 * CellType.STRING) { ef.setIRM(cell.getStringCellValue()); } else {
	 * errorList.add("Employee reporting manager type is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * else if(cid == 10) { if(cell != null) { if(cell.getCellType() ==
	 * CellType.STRING) { ef.setCurrentLocation(cell.getStringCellValue()); } else {
	 * errorList.add("Employee current location is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * else if(cid == 11) { if(cell != null) { if(cell.getCellType() ==
	 * CellType.STRING) { ef.setCurrentAllocation(cell.getStringCellValue()); } else
	 * { errorList.add("Employee current allocation is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * else if(cid == 12) { if(cell != null) { if(cell.getCellType() ==
	 * CellType.STRING) { ef.setProject(cell.getStringCellValue()); } else {
	 * errorList.add("Employee project is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } }
	 * 
	 * 
	 * 
	 * 
	 * if(cid > 12 && cid <= 12+skills.size()) { if(skills != null && skills.size()
	 * > 0) {
	 * 
	 * List<Skill> esrl = new ArrayList<Skill>();
	 * 
	 * Skill esr = null;
	 * 
	 * for(int s = 0; s < skills.size(); s++) {
	 * 
	 * for(int k=0; k<2; k++) {
	 * 
	 * if(k == 0) { esr = new Skill(); //esr.setSkill(skills.get(s));
	 * 
	 * if(cell != null) { if(cell.getCellType() == CellType.NUMERIC) {
	 * esr.setSkillId((int)cell.getNumericCellValue()); } else {
	 * errorList.add("Enter skill id in numeric integer format in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1)); } } else
	 * { errorList.add("skill id is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } } else { if(cell != null) { if(cell.getCellType() ==
	 * CellType.STRING) { esr.setSkillName(cell.getStringCellValue()); } else {
	 * errorList.add("Skill name  is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); }
	 * 
	 * esrl.add(esr);
	 * 
	 * }
	 * 
	 * cid++; j++; cell = inputRow.getCell(j); }
	 * 
	 * } ef.setSkill(esrl); } cid--;j--;
	 * 
	 * }
	 * 
	 * else if(cid == noOfColumns - 2) { if(cell != null) { }
	 * ef.setComments(cell.getStringCellValue()); } else {
	 * errorList.add("Overall Comments is empty in cell - " +
	 * CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) +
	 * ". Please enter."); } } else if(cid == noOfColumns - 1 && cell != null) {
	 * ef.setSuggestion(cell.getStringCellValue()); }
	 * 
	 * cid++; }
	 * 
	 * 
	 * //ef.setCreatedOn(LocalDateTime.now());
	 * 
	 * ef.setCreatedBy(createdBy); employee.add(ef); }
	 * 
	 * if(errorList.size() == 0) {
	 * 
	 * String fileName = file.getOriginalFilename(); EmployeeFile employeeFile =
	 * this.employeeFile(fileName, createdBy);
	 * 
	 * // EmployeeFile employeeFile = this.employeeFile(fileName, createdBy);
	 * 
	 * if(employeeFile != null && employeeFile.getId() > 0) {
	 * 
	 * String fileUploadPath = env.getProperty("EMPLOYEE_FILE_UPLOAD_PATH"); String
	 * filePath = fileUploadPath + File.separator + employeeFile.getId() + "." +
	 * fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	 * 
	 * try { File f = new File(filePath); f.createNewFile(); } catch (IOException e)
	 * {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * employee.forEach((e -> e.setEmployeeFile(employeeFile)));
	 * 
	 * }
	 * 
	 * employeeRepository.saveAll(employee); }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * else { errorList.add("Please upload file in .xlsx format"); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * return errorList; }
	 */

	/*
	 * private EmployeeFile employeeFile(String fileName, User uploadedBy) {
	 * 
	 * EmployeeFile employeeFile = new EmployeeFile();
	 * 
	 * employeeFile.setFileName(fileName); employeeFile.setUploadedBy(uploadedBy);
	 * employeeFile.setUploadedOn(LocalDateTime.now());
	 * 
	 * employeeFileRepository.save(employeeFile);
	 * 
	 * return employeeFile; }
	 */
		 
		
	/*
	 * public XSSFWorkbook downloadEmployeeTemplate() {
	 * 
	 * String methodName = "downloadEmployeeTemplate()"; LOGGER.info(methodName +
	 * " called");
	 * 
	 * int cellCount = 0;
	 * 
	 * List<Skill> skills = skillRepository.findAll();
	 * 
	 * XSSFWorkbook workbook = new XSSFWorkbook();
	 * 
	 * XSSFSheet sheet = workbook.createSheet("Employee-feedback");
	 * 
	 * Row topRow = sheet.createRow(0);
	 * 
	 * Row headerRow = sheet.createRow(1);
	 * 
	 * XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
	 * style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
	 * style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 * 
	 * Font font = workbook.createFont();
	 * font.setColor(IndexedColors.BLACK.getIndex()); style.setFont(font);
	 * 
	 * CellStyle style1 = workbook.createCellStyle();
	 * style1.setFillForegroundColor(IndexedColors.RED.getIndex());
	 * style1.setAlignment(HorizontalAlignment.CENTER);
	 * 
	 * Font font1 = workbook.createFont();
	 * font1.setColor(IndexedColors.RED.getIndex()); font1.setBold(true);
	 * style1.setFont(font1);
	 * 
	 * topRow.createCell(0).setCellValue("Note: Please give rating between 1 to 5."
	 * ); topRow.getCell(0).setCellStyle(style1);
	 * sheet.addMergedRegion(CellRangeAddress.valueOf("A1:D1"));
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Sno");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Emp Id");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Emp Name");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Emp Email");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue(" Emp Designation");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Emp Grade");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Emp Resource Type");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Emp DOJ");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Total Experience (In Years)");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Emp IRM");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Current Loaction");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Current Allocation");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Project");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * if(skills != null && skills.size() > 0) {
	 * 
	 * for(int i = 0; i < skills.size(); i++) {
	 * 
	 * topRow.createCell(cellCount).setCellValue(skills.get(i).getSkillName());
	 * topRow.getCell(cellCount).setCellStyle(style1);
	 * 
	 * sheet.addMergedRegion(CellRangeAddress.valueOf(CellReference.
	 * convertNumToColString(cellCount)+""+1 +":"+
	 * CellReference.convertNumToColString(cellCount+1)+""+1));
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Skill Id");
	 * headerRow.getCell(cellCount++).setCellStyle(style);
	 * 
	 * headerRow.createCell(cellCount).setCellValue("Skill Name");
	 * headerRow.getCell(cellCount++).setCellStyle(style); } }
	 * 
	 * 
	 * 
	 * autoSizeColumns(sheet, cellCount);
	 * 
	 * return workbook; }
	 * 
	 * private void autoSizeColumns(XSSFSheet workSheet, int columnCount) {
	 * 
	 * if(workSheet != null && columnCount > 0) { for(int i=0; i<columnCount; i++) {
	 * workSheet.autoSizeColumn(i); } } }
	 */
	
public List<String> uploadEmployeeFile(MultipartFile file, User createdBy) throws IOException {
		
		LOGGER.info("In Employee Service....upload employee data ");
		
		List<String> errorList = new ArrayList<String>();
		
		List<Employee> employees = new ArrayList<>();
		
		if(file != null) {
			try {
				
				String filetype = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
				
				if(filetype.equalsIgnoreCase("xlsx"))
				{
					List<Skill> skills = skillRepository.findAll();
					
					XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
					
					int startRow = 2;
					XSSFRow inputRow = null;
					XSSFCell cell = null;
					
					if(workbook != null && workbook.getSheetAt(0) != null) {
						
						XSSFSheet sheet = workbook.getSheetAt(0);
						
						int totalRows = sheet.getPhysicalNumberOfRows();
						int noOfColumns = sheet.getRow(1).getPhysicalNumberOfCells();
				
						for(int i = startRow; i < totalRows; i++) {
							
							inputRow = sheet.getRow(i);
							
							if(inputRow == null) continue;
							
							Employee e = new Employee();
							
							int cid = 1;
							
							for(int j = 1; j < noOfColumns; j++) {
								
								cell = inputRow.getCell(j);

								if(cid == 1) {
									
									if(cell != null) {
										if(cell.getCellTypeEnum() == CellType.NUMERIC) {
											e.setEmployeeId((int)cell.getNumericCellValue());
										}
										else {
											errorList.add("Enter employee id in numeric integer format in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1));
										}
									}
									else {
										errorList.add("Employee id is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								
								else if(cid == 2) {
									if(cell != null) {
										e.setEmployeeName(cell.getStringCellValue());
									}
									else {
										errorList.add("Employee name is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 3) {
									if(cell != null) {
										e.setEmail(cell.getStringCellValue());
									}
									else {
										errorList.add("Email is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 4) {
									if(cell != null) {
										e.setDesignation(cell.getStringCellValue());
									}
									else {
										errorList.add("Designation is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 5) {
									if(cell != null) {
										e.setGrade(cell.getStringCellValue());
									}
									else {
										errorList.add("Grade is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 6) {
									if(cell != null) {
										e.setResourceType(cell.getStringCellValue());
									}
									else {
										errorList.add("Resource Type is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 7) {
									if(cell != null) {
										e.setDOJ(this.convertToExcelDate(cell.getDateCellValue()));
									}
									else {
										errorList.add("Date Of Joining is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 8) {
									if(cell != null) {
										e.setTotalExp(cell.getNumericCellValue());
									}
									else {
										errorList.add("Total Exp is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 9) {
									if(cell != null) {
										e.setIRM(cell.getStringCellValue());
									}
									else {
										errorList.add("IRM is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 10) {
									if(cell != null) {
										e.setCurrentLocation(cell.getStringCellValue());
									}
									else {
										errorList.add("Current Location is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 11) {
									if(cell != null) {
										e.setCurrentAllocation(cell.getStringCellValue());
									}
									else {
										errorList.add("Current Allocation is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 12) {
									if(cell != null) {
										e.setProject(cell.getStringCellValue());
									}
									else {
										errorList.add("Project is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}

								if(cid > 12 && cid <=12+skills.size()) {
							
									if(skills != null && skills.size() > 0) {
										
										List<Skill> employeeSkills = new ArrayList<Skill>();
										
										Skill s = null;
										
										for(int k = 0; k < skills.size(); k++) {
											if(cell != null) {
												if(cell.getStringCellValue().equalsIgnoreCase("YES")) {
													s = new Skill();
													s.setId(skills.get(k).getId());
												}
											}
											else {
												errorList.add("Skill in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter value Yes Or No.");
											}
											
											if(s != null && s.getId() > 0) {
												employeeSkills.add(s);
											}

											cid++;
											j++;
											cell = inputRow.getCell(j);
										}
										
										e.setSkills(employeeSkills);
									}
									
								}
								cid++;
							}
							e.setCreatedBy(createdBy);
							employees.add(e);
						}
						
						if(errorList.size() == 0) {
							String fileName = file.getOriginalFilename();
							
							EmployeeFile  employeeFile = this.employeeFile(fileName, createdBy);
							
							if(employeeFile != null && employeeFile.getId() > 0) {
								
								String fileUploadPath = env.getProperty("EMPLOYEE_FILE_UPLOAD_PATH");
								String filePath = fileUploadPath + File.separator + employeeFile.getId() + "." +
										fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

								try {
									File f = new File(filePath);
									f.createNewFile();
								}
								catch (IOException e) {
									e.printStackTrace();
								}
								
								employees.forEach(e -> e.setEmployeeFile(employeeFile));
							}
							
							employeeRepository.saveAll(employees);
						}
					}
				}
				else {
					errorList.add("Please upload only xlsx format file.");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return errorList;
	}
	
	private EmployeeFile  employeeFile(String fileName, User uploadedBy) {
		
		EmployeeFile  employeeFile = new EmployeeFile();
		
		employeeFile.setFileName(fileName);
		employeeFile.setUploadedBy(uploadedBy);
		employeeFile.setUploadedOn(LocalDateTime.now());
		
		employeeFileRepository.save(employeeFile);
		
		return employeeFile;
	}
	
	private String convertToExcelDate(Date date) {
		String convertDate = null;
		if(date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			convertDate = dateFormat.format(date);
		}
		return convertDate;
	}
	
	
	 public XSSFWorkbook downloadEmployeeTemplate() {
		  
		  String methodName = "downloadEmployeeTemplate()"; LOGGER.info(methodName +
		  " called");
		  
		  int cellCount = 0;
		  
		  List<Skill> skills = skillRepository.findAll();
		  
		  XSSFWorkbook workbook = new XSSFWorkbook();
		  
		  XSSFSheet sheet = workbook.createSheet("Employee");
		  
		  Row topRow = sheet.createRow(0);
		  
		  Row headerRow = sheet.createRow(1);
		  
		  XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
		  style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		  style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		  
		  Font font = workbook.createFont();
		  font.setColor(IndexedColors.BLACK.getIndex()); style.setFont(font);
		  
		  CellStyle style1 = workbook.createCellStyle();
		  style1.setFillForegroundColor(IndexedColors.RED.getIndex());
		  style1.setAlignment(HorizontalAlignment.CENTER);
		  
		  Font font1 = workbook.createFont();
		  font1.setColor(IndexedColors.RED.getIndex()); font1.setBold(true);
		  style1.setFont(font1);
		  
		  topRow.createCell(0).setCellValue("Note: Please add employee information here"); 
		  topRow.getCell(0).setCellStyle(style1);
		  sheet.addMergedRegion(CellRangeAddress.valueOf("A1:M1"));
		  
		  headerRow.createCell(cellCount).setCellValue("Sno");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Emp Id");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Emp Name");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Emp Email");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue(" Emp Designation");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Emp Grade");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Emp Resource Type");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Emp DOJ");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Total Experience (In Years)");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Emp IRM");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Current Loaction");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Current Allocation");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  headerRow.createCell(cellCount).setCellValue("Project");
		  headerRow.getCell(cellCount++).setCellStyle(style);
		  
		  if(skills != null && skills.size() > 0) {
		  
		  topRow.createCell(cellCount).setCellValue("Skill");
			topRow.getCell(cellCount).setCellStyle(style1);

			sheet.addMergedRegion(CellRangeAddress.valueOf(CellReference.convertNumToColString(cellCount)+""+1 +":"+ CellReference.convertNumToColString(cellCount+skills.size()-1)+""+1));
				
		  for(int i = 0; i < skills.size(); i++) {
			  
			  headerRow.createCell(cellCount).setCellValue(skills.get(i).getName());
			  headerRow.getCell(cellCount++).setCellStyle(style);
		  
			  }
		  }
		  
		  autoSizeColumns(sheet, cellCount);
		  
		  return workbook; }
		  
		  private void autoSizeColumns(XSSFSheet workSheet, int columnCount) {
		  
		  if(workSheet != null && columnCount > 0) { for(int i=0; i<columnCount; i++) {
		  workSheet.autoSizeColumn(i); } } }
		 
		
		
}
