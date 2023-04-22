package com.yash.ems.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="employee_feedback")
public class EmployeeFeedback implements Serializable{

	private static final long serialVersionUID = -5920333604782942132L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="overall_experience")
//	@NotEmpty(message = "overall experience should not be blank")
	private int overallExperience;
	
	@Column(name="project_experience")
//	@NotEmpty(message = "project experience should not be blank")
	private int projectExperience;
	
	@Column(name="comments")
//	@NotEmpty(message = "comments should not be blank")
	private String comments;
	
	@Column(name="suggestion")
	private String suggestion;
	
	@Column(name="created_on")
	private LocalDateTime createdOn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private EmployeeFeedbackFile employeeFeedbackFile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_id", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private User createdBy;
	
	@JsonManagedReference
	@OneToMany(targetEntity = EmployeeSkillsRating.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_feedback_id", referencedColumnName = "id")
	private List<EmployeeSkillsRating> employeeSkillsRatings;

	public EmployeeFeedback() {
		super();
	}

	public EmployeeFeedback(int id, int overallExperience, int projectExperience, String comments, String suggestion,
			LocalDateTime createdOn, Employee employee, EmployeeFeedbackFile employeeFeedbackFile, User createdBy,
			List<EmployeeSkillsRating> employeeSkillsRatings) {
		super();
		this.id = id;
		this.overallExperience = overallExperience;
		this.projectExperience = projectExperience;
		this.comments = comments;
		this.suggestion = suggestion;
		this.createdOn = createdOn;
		this.employee = employee;
		this.employeeFeedbackFile = employeeFeedbackFile;
		this.createdBy = createdBy;
		this.employeeSkillsRatings = employeeSkillsRatings;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOverallExperience() {
		return overallExperience;
	}
	public void setOverallExperience(int overallExperience) {
		this.overallExperience = overallExperience;
	}
	public int getProjectExperience() {
		return projectExperience;
	}
	public void setProjctExperience(int projectExperience) {
		this.projectExperience = projectExperience;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public EmployeeFeedbackFile getEmployeeFeedbackFile() {
		return employeeFeedbackFile;
	}
	public void setEmployeeFeedbackFile(EmployeeFeedbackFile employeeFeedbackFile) {
		this.employeeFeedbackFile = employeeFeedbackFile;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public List<EmployeeSkillsRating> getEmployeeSkillsRatings() {
		return employeeSkillsRatings;
	}
	public void setEmployeeSkillsRatings(List<EmployeeSkillsRating> employeeSkillsRatings) {
		this.employeeSkillsRatings = employeeSkillsRatings;
	}
}
