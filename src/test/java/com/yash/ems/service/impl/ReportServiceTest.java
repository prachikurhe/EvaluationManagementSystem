package com.yash.ems.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.yash.ems.converter.UserReportDtoConverter;
import com.yash.ems.dto.UserReportDto;
import com.yash.ems.model.Employee;
import com.yash.ems.model.EmployeeFeedback;
import com.yash.ems.model.EmployeeSkillsRating;
import com.yash.ems.model.Skill;
import com.yash.ems.model.User;
import com.yash.ems.repo.EmployeeFeedbackRepository;
import com.yash.ems.service.impl.ReportServiceImpl;
import com.yash.ems.services.ReportService;

@SpringBootTest(classes = { ReportServiceTest.class })
public class ReportServiceTest {

	private ReportServiceImpl reportService;

	@Mock
	private EmployeeFeedbackRepository feedbackRepository;

	@Test
	void getAllReports() {
		reportService = new ReportServiceImpl();
		reportService.setFeedbackRepository(feedbackRepository);
		reportService.setUserReportDtoConverter(new UserReportDtoConverter());

		EmployeeFeedback e1 = EmployeeFeedbackFixture.prepareEmployeeFeedback();
		when(feedbackRepository.findAllByOrderByIdDesc()).thenReturn(List.of(e1));

		List<UserReportDto> reports = reportService.getReports();

		Assertions.assertNotNull(reports);
		assertEquals(1, reports.size());
		assertEquals(3, reports.get(0).getOverallExperience());
	}

	@Test
	void getAllFeedbackById() {
		reportService = new ReportServiceImpl();
		reportService.setFeedbackRepository(feedbackRepository);
		reportService.setUserReportDtoConverter(new UserReportDtoConverter());

		EmployeeFeedback e1 = EmployeeFeedbackFixture.prepareEmployeeFeedback();
		when(feedbackRepository.findById(101)).thenReturn(Optional.of(e1));

		UserReportDto feedback = reportService.getReportByFeedBackId(101);

		Assertions.assertNotNull(feedback);
		assertEquals("Prachi", feedback.getEmployeeName());

	}

	/*
	 * @Test void saveFeedbackById(){ reportService = new
	 * ReportServiceImpl(feedbackRepository, new UserReportDtoConverter());
	 * 
	 * EmployeeFeedback e1 = EmployeeFeedbackFixture.prepareEmployeeFeedback();
	 * when(feedbackRepository.save(e1)).thenReturn(e1);
	 * 
	 * EmployeeFeedback feedback = feedbackRepository.save(e1);
	 * 
	 * Assertions.assertNotNull(feedback);
	 * assertEquals("Prachi",feedback.getEmployee().getEmployeeName());
	 * 
	 * }
	 */
}

class EmployeeFeedbackFixture {
	public static EmployeeFeedback prepareEmployeeFeedback() {
		EmployeeFeedback employeeFeedback = new EmployeeFeedback();
		Employee employee = new Employee();
//        employee.set("xyz");
//        employee.setId(1);
		employee.setEmployeeId(employee.getId());
		employee.setEmployeeName("Prachi");
		employee.setTotalExp(3.2);
		employeeFeedback.setEmployee(employee);
		employeeFeedback.setComments("Good in java");
		User createdBy = new User();
//        createdBy.setCode("xyz");
//        createdBy.setId(202);
//        createdBy.setName("Admin");
		createdBy.setEmail("admin@yash.com");
		createdBy.setPassword("pass");
//        employeeFeedback.setId(101);
		employeeFeedback.setCreatedBy(createdBy);
		employeeFeedback.setOverallExperience(3);
		employeeFeedback.setProjctExperience(3);
		employeeFeedback.setCreatedOn(LocalDateTime.now());
		employeeFeedback.setEmployeeSkillsRatings(List.of(prepareEmployeeSkillRaiting(1, "java", "1 - Poor"),
				prepareEmployeeSkillRaiting(2, "oracle", "2 - Below Average"), prepareEmployeeSkillRaiting(2, "spring", "4 - Good")));
		employeeFeedback.setEmployeeFeedbackFile(null);
		return employeeFeedback;
	}

	private static EmployeeSkillsRating prepareEmployeeSkillRaiting(int id, String skillName, String rating) {
		EmployeeSkillsRating skillsRating = new EmployeeSkillsRating();
		Skill skill = new Skill();
		skill.setId(id);
		skill.setName(skillName);
		skillsRating.setSkill(skill);
		skillsRating.setId(id + 100);
		skillsRating.setRemarks("good in " + skillName);
		skillsRating.setRating(rating);
		return skillsRating;
	}
}
