package com.yash.ems.service.impl;


//import com.yash.ems.controller.ReportController;
import com.yash.ems.converter.UserReportDtoConverter;
import com.yash.ems.dto.UserReportDto;
import com.yash.ems.model.EmployeeFeedback;
import com.yash.ems.repo.EmployeeFeedbackRepository;
import com.yash.ems.services.ReportService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class ReportServiceImpl implements ReportService {

  private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
  @Autowired
  private EmployeeFeedbackRepository feedbackRepository;
  @Autowired
  private UserReportDtoConverter userReportDtoConverter;
  /**
   * @param feedbackId
   * @return
   */
  @Override
  public UserReportDto getReportByFeedBackId(int feedbackId) {

      String methodName = "getReportByFeedBackId()";
      logger.info(methodName + " called");

      EmployeeFeedback employeeFeedback = feedbackRepository.findById(feedbackId).orElse(null);
		return userReportDtoConverter.convert(employeeFeedback);
  }

   /**
   * @return
   */
  @Override
  public List<UserReportDto> getReports() {

      String methodName = "getAllReports()";
      logger.info(methodName + " called");

      List<EmployeeFeedback> employeeFeedbacks = feedbackRepository.findAllByOrderByIdDesc();
      return convertList(employeeFeedbacks);
  }

  private List<UserReportDto> convertList(List<EmployeeFeedback> employeeFeedbacks){
      List<UserReportDto> userReportDtoList = new ArrayList<>();
      if(nonNull(employeeFeedbacks)){
          for (EmployeeFeedback employeeFeedback :employeeFeedbacks){
              userReportDtoList.add(userReportDtoConverter.convert(employeeFeedback));
          }
      }
      return userReportDtoList;
  }
  
  public void setFeedbackRepository(EmployeeFeedbackRepository feedbackRepository) {
		this.feedbackRepository = feedbackRepository;
	}

	public void setUserReportDtoConverter(UserReportDtoConverter userReportDtoConverter) {
		this.userReportDtoConverter = userReportDtoConverter;
	}

}

