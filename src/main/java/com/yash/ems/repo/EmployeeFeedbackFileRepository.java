package com.yash.ems.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yash.ems.model.EmployeeFeedbackFile;

@Repository
public interface EmployeeFeedbackFileRepository extends JpaRepository<EmployeeFeedbackFile, Integer> {

}