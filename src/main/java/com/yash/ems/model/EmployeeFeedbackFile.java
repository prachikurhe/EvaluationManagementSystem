package com.yash.ems.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="employee_feedback_file")
public class EmployeeFeedbackFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="uploaded_on")
	private LocalDateTime uploadedOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uploaded_by_id", referencedColumnName = "id")
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private User uploadedBy;
	
	public EmployeeFeedbackFile() {
		super();
	}
	
	public EmployeeFeedbackFile(int id, String fileName, LocalDateTime uploadedOn, User uploadedBy) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.uploadedOn = uploadedOn;
		this.uploadedBy = uploadedBy;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public LocalDateTime getUploadedOn() {
		return uploadedOn;
	}
	public void setUploadedOn(LocalDateTime uploadedOn) {
		this.uploadedOn = uploadedOn;
	}
	public User getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(User uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
}
