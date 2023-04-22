package com.yash.ems.model;





import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Employee")
public class Employee {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name = "emp_id")
	private int employeeId;
	@NotEmpty(message = " employee name should not be empty")
	private String employeeName;
	@NotEmpty(message = "employee email should not be blank")
	private String email;
	@NotEmpty(message = "employee designation should not be empty")
	private String designation;
	@NotEmpty(message = "employee grade  should not be empty")
	private String grade;
	@NotEmpty(message = "resource type should not be empty")
	private String resourceType;
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING)
	private String DOJ;
	@NotNull(message = "employee total experience should not be empty")
	private Double totalExp;
	@NotEmpty(message = "employee reporting manager field should not be empty")
	private String IRM;
	@NotEmpty(message = "employee current location should not be blank")
	private String currentLocation;
	@NotEmpty(message = "employee current allocation should not be blank")
	private String currentAllocation;
	@NotEmpty(message = "employee project allocation should not be blank")
	private String project;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private EmployeeFile employeeFile;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_id", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private User createdBy;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "fk_emp_id" , referencedColumnName = "id")
//    private List<Skill> skill;
	
	@ManyToMany
	@JoinTable(
	  name = "employee_skill", 
	  joinColumns = @JoinColumn(name = "employee_id"), 
	  inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private List<Skill> skills;

	public Employee() {
		super();
	}

	public Employee(int id, int employeeId,
			@NotEmpty(message = " employee name should not be empty") String employeeName,
			@NotEmpty(message = "employee email should not be blank") String email,
			@NotEmpty(message = "employee designation should not be empty") String designation,
			@NotEmpty(message = "employee grade  should not be empty") String grade,
			@NotEmpty(message = "resource type should not be empty") String resourceType, String dOJ,
			@NotNull(message = "employee total experience should not be empty") Double totalExp,
			@NotEmpty(message = "employee reporting manager field should not be empty") String iRM,
			@NotEmpty(message = "employee current location should not be blank") String currentLocation,
			@NotEmpty(message = "employee current allocation should not be blank") String currentAllocation,
			@NotEmpty(message = "employee project allocation should not be blank") String project,
			EmployeeFile employeeFile, User createdBy, List<Skill> skills) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.email = email;
		this.designation = designation;
		this.grade = grade;
		this.resourceType = resourceType;
		DOJ = dOJ;
		this.totalExp = totalExp;
		IRM = iRM;
		this.currentLocation = currentLocation;
		this.currentAllocation = currentAllocation;
		this.project = project;
		this.employeeFile = employeeFile;
		this.createdBy = createdBy;
		this.skills = skills;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getDOJ() {
		return DOJ;
	}
	public void setDOJ(String dOJ) {
		DOJ = dOJ;
	}
	public Double getTotalExp() {
		return totalExp;
	}
	public void setTotalExp(Double totalExp) {
		this.totalExp = totalExp;
	}
	public String getIRM() {
		return IRM;
	}
	public void setIRM(String iRM) {
		IRM = iRM;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getCurrentAllocation() {
		return currentAllocation;
	}
	public void setCurrentAllocation(String currentAllocation) {
		this.currentAllocation = currentAllocation;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public EmployeeFile getEmployeeFile() {
		return employeeFile;
	}
	public void setEmployeeFile(EmployeeFile employeeFile) {
		this.employeeFile = employeeFile;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
//	public List<Skill> getSkill() {
//		return skill;
//	}
//	public void setSkill(List<Skill> skill) {
//		this.skill = skill;
//	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

//	@Override
//	public String toString() {
//		return "Employee [id=" + id + ", employeeId=" + employeeId + ", employeeName=" + employeeName + ", email="
//				+ email + ", designation=" + designation + ", grade=" + grade + ", resourceType=" + resourceType
//				+ ", DOJ=" + DOJ + ", totalExp=" + totalExp + ", IRM=" + IRM + ", currentLocation=" + currentLocation
//				+ ", currentAllocation=" + currentAllocation + ", project=" + project + ", employeeFile=" + employeeFile
//				+ ", createdBy=" + createdBy + ", skill=" + skill + "]";
//	}
	
}