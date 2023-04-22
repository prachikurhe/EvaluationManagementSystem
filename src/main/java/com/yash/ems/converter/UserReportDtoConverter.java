package com.yash.ems.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.yash.ems.dto.SkillResponseDto;
import com.yash.ems.dto.UserReportDto;
import com.yash.ems.model.Employee;
import com.yash.ems.model.EmployeeFeedback;
import com.yash.ems.model.EmployeeSkillsRating;
import com.yash.ems.model.Skill;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
public class UserReportDtoConverter implements Converter<EmployeeFeedback, UserReportDto> {
    /** 
     * This class is used to convert dto to entity
     * @param 
     * @return
     */
    @Override
    public UserReportDto convert(EmployeeFeedback feedback) {
        UserReportDto userReportDto = new UserReportDto();

        Employee employee = feedback.getEmployee();
        if (nonNull(employee)) {
            userReportDto.setEmployeeId(employee.getEmployeeId());
            userReportDto.setEmployeeName(employee.getEmployeeName());
        }

        userReportDto.setFeedbackId(feedback.getId());
        userReportDto.setComments(feedback.getComments());
        userReportDto.setSuggestion(feedback.getSuggestion());
        userReportDto.setOverallExperience(feedback.getOverallExperience());
        userReportDto.setProjectExperience(feedback.getProjectExperience());
        userReportDto.setCreatedOn(feedback.getCreatedOn());
        userReportDto.setCreatedBy(nonNull(feedback.getCreatedBy()) ? feedback.getCreatedBy().getUsername() : "");
        userReportDto.setSkillResponseList(setSkillResponseList(feedback.getEmployeeSkillsRatings()));

        return userReportDto;
    }

    private List<SkillResponseDto> setSkillResponseList(List<EmployeeSkillsRating> employeeSkillsRatings) {
        List<SkillResponseDto> skillResponseDtoList = new ArrayList<>();
        if (nonNull(employeeSkillsRatings) && !employeeSkillsRatings.isEmpty()) {
            for (EmployeeSkillsRating skillsRating : employeeSkillsRatings) {
                SkillResponseDto skillResponseDto = new SkillResponseDto();
                Skill skill = skillsRating.getSkill();
                if(nonNull(skill)){
                    skillResponseDto.setSkillId(skill.getId());
                    skillResponseDto.setSkillName(skill.getName());
                }
                skillResponseDto.setRemarks(skillsRating.getRemarks());
                if(nonNull(skillsRating.getRating())) {
                	String[] split = skillsRating.getRating().split("-");
                	if(split.length > 1) {
                		skillResponseDto.setRatingReceived(split[0]);		
                	}
                }
                skillResponseDtoList.add(skillResponseDto);
            }
        }
        return skillResponseDtoList;
    }
}

