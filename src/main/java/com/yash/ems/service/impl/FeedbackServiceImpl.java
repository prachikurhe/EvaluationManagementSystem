package com.yash.ems.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yash.ems.config.LoggerConfiguration;
import com.yash.ems.model.Employee;
import com.yash.ems.model.EmployeeFeedback;
import com.yash.ems.model.EmployeeFeedbackFile;
import com.yash.ems.model.EmployeeSkillsRating;
import com.yash.ems.model.Skill;
import com.yash.ems.model.User;
import com.yash.ems.repo.EmployeeFeedbackFileRepository;
import com.yash.ems.repo.EmployeeFeedbackRepository;
import com.yash.ems.repo.EmployeeRepository;
import com.yash.ems.repo.SkillRepository;
import com.yash.ems.services.FeedbackService;


@Service
public class FeedbackServiceImpl implements FeedbackService {

	Logger logger = LoggerConfiguration.getLogger(FeedbackServiceImpl.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private EmployeeFeedbackRepository employeeFeedbackRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private SkillRepository skillRepository; 
	
	@Autowired
	private EmployeeFeedbackFileRepository employeeFeedbackFileRepository; 
	
	@Override
	public List<Skill> getSkills() {
		
		String methodName = "getSkills()";
		logger.info(methodName + " called"); 
	
		return skillRepository.findAll();
	}
	
	@Override
	public List<Employee> getEmployees() {
		
		String methodName = "getEmployees()";
		logger.info(methodName + " called"); 
		
		return employeeRepository.findAll();
	}
	
	@Override
	public List<Employee> getFeedbackEmployees() {
		
		String methodName = "getFeedbackEmployees()";
		logger.info(methodName + " called"); 
		
		return employeeRepository.getFeedbackEmployees();
	}

	@Override
	public List<EmployeeFeedback> getAllEmployeeFedback() {
		
		String methodName = "getAllEmployeeFedback()";
		logger.info(methodName + " called"); 
		
		return employeeFeedbackRepository.findAll();
	}
	
	@Override
	public List<EmployeeFeedback> getEmployeeFedbacksByEmployeeId(int id) {
		String methodName = "getEmployeeFedbacksByEmployeeId()";
		logger.info(methodName + " called"); 
		
		return employeeFeedbackRepository.getEmployeeFedbacksByEmployeeId(id);
	}
	
	@Override
	public EmployeeFeedback saveEmployeeFeedback(EmployeeFeedback employeeFeedback) {
		
		String methodName = "savEmployeeFeedback()";
		logger.info(methodName + " called"); 
		
		if(employeeFeedback != null) {
			
			employeeFeedback.setCreatedOn(LocalDateTime.now());
			
			employeeFeedback = employeeFeedbackRepository.save(employeeFeedback);
		}
		
		return employeeFeedback;
	}
	
	@Override
	public List<String> uploadEmployeeFeedback(MultipartFile file, User createdBy)throws IOException {
		
		String methodName = "uploadEmployeeFeedback()";
		logger.info(methodName + " called");
		
		List<String> errorList = new ArrayList<String>();
		
		List<EmployeeFeedback> employeeFeedbacks = new ArrayList<>();
		
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
							
							EmployeeFeedback ef = new EmployeeFeedback();
							
							int cid = 1;
							String empId = "", empName = "";
							
							for(int j = 1; j < noOfColumns; j++) {
								
								cell = inputRow.getCell(j);
				
								if(cid == 1) {
									if(cell != null) {
										
										empName = cell.getStringCellValue();
									}
									else {
										errorList.add("Employee name is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 2) {
									if(cell != null) {
										
										if(cell.getCellTypeEnum() == CellType.NUMERIC) {

											empId = String.valueOf(String.format("%.0f", cell.getNumericCellValue()));

											if(empName != null && empName.length() > 0 && empId != null && empId.length() > 0) {
			
												List<Employee> employees = employeeRepository.getEmployeesByNameAndId(empName, empId);
								
												if(employees != null && employees.size() > 1) {
													errorList.add("Multiple records available with employee name = "+empName +" and employee id : "+empId
															+". Please enter valid employe name and employee id in cell - " + CellReference.convertNumToColString(j-1)+""+(inputRow.getRowNum()+1)
															+ " and cell - "+CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1));
												}
												else if(employees != null && employees.size() == 1)
													ef.setEmployee(employees.get(0));
												else {
													errorList.add("Enter valid employe name in cell - " + CellReference.convertNumToColString(j-1)+""+(inputRow.getRowNum()+1));
													errorList.add("Enter valid employee id in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1));
												}
											}
										}
										else {
											errorList.add("Enter employee id in numeric integer format in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1));
										}
									}
									else {
										errorList.add("Employee code is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 3) {
									if(cell != null) {
										if(cell.getCellTypeEnum() == CellType.NUMERIC) {
											ef.setOverallExperience((int)cell.getNumericCellValue());
										}
										else {
											errorList.add("Enter overall experience in numeric integer format in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1));
										}
									}
									else {
										errorList.add("Overall Experience code is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == 4) {
									if(cell != null) {
										if(cell.getCellTypeEnum() == CellType.NUMERIC) {
											ef.setProjctExperience((int)cell.getNumericCellValue());
										}
										else {
											errorList.add("Enter projct experience in numeric integer format in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1));
										}
									}
									else {
										errorList.add("Projct Experience code is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}

								if(cid > 4 && cid <= 4+skills.size()) {
									if(skills != null && skills.size() > 0) {
										
										List<EmployeeSkillsRating> esrl = new ArrayList<EmployeeSkillsRating>();
										
										EmployeeSkillsRating esr = null;
										
										for(int s = 0; s < skills.size(); s++) {
											
											for(int k=0; k<2; k++) {
												esr = new EmployeeSkillsRating();
												if(k == 0) {
													if(cell != null) {
														esr.setSkill(skills.get(s));
														esr.setRating(cell.getStringCellValue());
													}
												}
												else {
													if(cell != null) {
														esr.setSkill(skills.get(s));
														esr.setRemarks(cell.getStringCellValue());
													}
												}
												if(k==1 && esr != null && (esr.getRating() != null || esr.getRemarks() != null))
													esrl.add(esr);

												cid++;
												j++;
												cell = inputRow.getCell(j);
											}
											
										}
										ef.setEmployeeSkillsRatings(esrl);
									}
									cid--;j--;
								}
								
								else if(cid == noOfColumns - 2) {
									if(cell != null) {
										ef.setComments(cell.getStringCellValue());
									}
									else {
										errorList.add("Overall Comments is empty in cell - " + CellReference.convertNumToColString(j)+""+(inputRow.getRowNum()+1) + ". Please enter.");
									}
								}
								else if(cid == noOfColumns - 1 && cell != null) {
									ef.setSuggestion(cell.getStringCellValue());
								}
								
								cid++;
							}
							
							ef.setCreatedOn(LocalDateTime.now());
							ef.setCreatedBy(createdBy);
							employeeFeedbacks.add(ef);
						}
						
						if(errorList.size() == 0) {
							
							String fileName = file.getOriginalFilename();
							
							EmployeeFeedbackFile  employeeFeedbackFile = this.employeeFeedbackFile(fileName, createdBy);
							
							if(employeeFeedbackFile != null && employeeFeedbackFile.getId() > 0) {
							
								String fileUploadPath = env.getProperty("EMPLOYEE_FEEDBACK_FILE_UPLOAD_PATH");
								String filePath = fileUploadPath + File.separator + employeeFeedbackFile.getId() + "." +
										fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

								try {
									File f = new File(filePath);
									f.createNewFile();
								}
								catch (IOException e) {
									e.printStackTrace();
								}
								
								employeeFeedbacks.forEach(e -> e.setEmployeeFeedbackFile(employeeFeedbackFile));
							}
							
							employeeFeedbackRepository.saveAll(employeeFeedbacks);
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
	
	private EmployeeFeedbackFile  employeeFeedbackFile(String fileName, User uploadedBy) {
		
		EmployeeFeedbackFile  employeeFeedbackFile = new EmployeeFeedbackFile();
		
		employeeFeedbackFile.setFileName(fileName);
		employeeFeedbackFile.setUploadedBy(uploadedBy);
		employeeFeedbackFile.setUploadedOn(LocalDateTime.now());
		
		employeeFeedbackFileRepository.save(employeeFeedbackFile);
		
		return employeeFeedbackFile;
	}
	
	@Override
	public XSSFWorkbook downloadEmployeeFeedbackTemplate() {
		
		String methodName = "downloadEmployeeFeedbackTemplate()";
		logger.info(methodName + " called");
		
		int cellCount = 0;
		
		List<Skill> skills = skillRepository.findAll();

		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet("Employee-feedback");
		
		//hidden sheets for rating
		XSSFSheet ratingHiddenSheet = workbook.createSheet("rating");
		//hidden sheets for Overall Experience and Project Experience
		XSSFSheet experienceHiddenSheet = workbook.createSheet("experience");
		
		List<String> ratingList = this.ratingList();
		
		for(int i=0; i<this.ratingList().size(); i++) {
			XSSFRow row = ratingHiddenSheet.createRow(i);
			row.createCell(0).setCellValue(ratingList.get(i));
		}
		ratingHiddenSheet.setColumnHidden(1, true);
		ratingHiddenSheet.protectSheet("CompetencyEMS@12345");
		workbook.setSheetHidden(workbook.getSheetIndex(ratingHiddenSheet), true); 
		
		List<Integer> experienceList = this.experienceList();
		
		for(int i=0; i<experienceList().size(); i++) {
			XSSFRow row = experienceHiddenSheet.createRow(i);
			row.createCell(0).setCellValue(experienceList.get(i));
		} 
		experienceHiddenSheet.setColumnHidden(1, true);
		experienceHiddenSheet.protectSheet("CompetencyEMS@12345");
		workbook.setSheetHidden(workbook.getSheetIndex(experienceHiddenSheet), true); 
		
		Row topRow = sheet.createRow(0);
		
		Row headerRow = sheet.createRow(1);
		
		XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Font font = workbook.createFont();
		font.setColor(IndexedColors.BLACK.getIndex());
		style.setFont(font);
		
		CellStyle style1 = workbook.createCellStyle();
		style1.setFillForegroundColor(IndexedColors.RED.getIndex());
		style1.setAlignment(HorizontalAlignment.CENTER);
		
		Font font1 = workbook.createFont();
		font1.setColor(IndexedColors.RED.getIndex());
		font1.setBold(true);
		style1.setFont(font1);
		
		topRow.createCell(0).setCellValue("Note: Please select dropdown for Overall Experience, Project Experience and Rating.");
		topRow.getCell(0).setCellStyle(style1);
		sheet.addMergedRegion(CellRangeAddress.valueOf("A1:E1"));

		headerRow.createCell(cellCount).setCellValue("Sno");
		headerRow.getCell(cellCount++).setCellStyle(style);
		
		headerRow.createCell(cellCount).setCellValue("Employee Name");
		headerRow.getCell(cellCount++).setCellStyle(style);

		headerRow.createCell(cellCount).setCellValue("Employee Id");
		headerRow.getCell(cellCount++).setCellStyle(style);

		this.setCellValidationForHiddenSheet(sheet, experienceHiddenSheet.getSheetName(), 2000, cellCount, cellCount, 10);
		headerRow.createCell(cellCount).setCellValue("Overall Experience (In Years)");
		headerRow.getCell(cellCount++).setCellStyle(style);

		this.setCellValidationForHiddenSheet(sheet, experienceHiddenSheet.getSheetName(), 2000, cellCount, cellCount, 10);
		headerRow.createCell(cellCount).setCellValue("Project Experience (In Years)");
		headerRow.getCell(cellCount++).setCellStyle(style);
		
		if(skills != null && skills.size() > 0) {
			
			for(int i = 0; i < skills.size(); i++) {

				topRow.createCell(cellCount).setCellValue(skills.get(i).getName());
				topRow.getCell(cellCount).setCellStyle(style1);
	
				sheet.addMergedRegion(CellRangeAddress.valueOf(CellReference.convertNumToColString(cellCount)+""+1 +":"+ CellReference.convertNumToColString(cellCount+1)+""+1));
				
				this.setCellValidationForHiddenSheet(sheet, ratingHiddenSheet.getSheetName(), 2000, cellCount, cellCount, 5);

				headerRow.createCell(cellCount).setCellValue("Rating");
				headerRow.getCell(cellCount++).setCellStyle(style);
				
				headerRow.createCell(cellCount).setCellValue("Comment");
				headerRow.getCell(cellCount++).setCellStyle(style);
			}
		}
		
		headerRow.createCell(cellCount).setCellValue("Overall Comments");
		headerRow.getCell(cellCount++).setCellStyle(style);

		headerRow.createCell(cellCount).setCellValue("Suggestion");
		headerRow.getCell(cellCount++).setCellStyle(style);
	
		autoSizeColumns(sheet, cellCount);
		
		return workbook;
	}
	
	private void autoSizeColumns(XSSFSheet workSheet, int columnCount) {
		
		if(workSheet != null && columnCount > 0) {
			for(int i=0; i<columnCount; i++) {
				workSheet.autoSizeColumn(i);
			}
		}
	}
	
	private List<String> ratingList() {
		
		List<String> list = Arrays.asList("1 - Poor", "2 - Below Average", "3 - Average", "4 - Good", "5 - Excellent");
		
		return list;
	}
	
	private List<Integer> experienceList() {
		
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		return list;
	}
	
	private void setCellValidationForHiddenSheet(XSSFSheet sheet, String hiddenSheet, int totalRow, int firstColumn, int lastColumn, int dataList) {
		
		DataValidationHelper helper = new XSSFDataValidationHelper(sheet);
//First 2 Values for rows and last 2 are for column - here column is single.		
		CellRangeAddressList addressList = new CellRangeAddressList(2, totalRow, firstColumn, lastColumn);
		DataValidationConstraint constraint = helper.createFormulaListConstraint(hiddenSheet+"!$A$1:$A$"+dataList);
		DataValidation dataValidation = helper.createValidation(constraint, addressList);
		dataValidation.setSuppressDropDownArrow(true);
		dataValidation.setShowErrorBox(true);
		dataValidation.setShowPromptBox(true);
		sheet.addValidationData(dataValidation);
	}
}