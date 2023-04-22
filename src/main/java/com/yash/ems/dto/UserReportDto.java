package com.yash.ems.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserReportDto implements Serializable {
	private static final long serialVersionUID = 5807490953805939592L;
	private long employeeId;
    private String employeeName;
    private long feedbackId;
    private int overallExperience;
    private int projectExperience;
    private String comments;
    private String suggestion;
    private String createdBy;
    private LocalDateTime createdOn;
    private List<SkillResponseDto> skillResponseList;
    
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public long getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(long feedbackId) {
		this.feedbackId = feedbackId;
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
	public void setProjectExperience(int projectExperience) {
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public List<SkillResponseDto> getSkillResponseList() {
		return skillResponseList;
	}
	public void setSkillResponseList(List<SkillResponseDto> skillResponseList) {
		this.skillResponseList = skillResponseList;
	}
}
