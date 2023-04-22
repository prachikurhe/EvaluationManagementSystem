package com.yash.ems.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.ems.dto.UserReportDto;
import com.yash.ems.services.ReportService;

import io.swagger.annotations.ApiOperation;


/**
 * This class is used to expose rest api for report module.
 *
 * @author prachi.kurhe
 * @since 01-03-2023
 */
@RestController
@RequestMapping("/reports")
@CrossOrigin("*")
public class ReportController {
	@Autowired
    private ReportService reportService;

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    /**
     * @param feedbackId  This is the unique id of employee.
     *
     * @return This will return user of report
     */
    @GetMapping("/{feedbackId}")
    @ApiOperation(value = "fetch report by feedbackId.")
    public UserReportDto getReportByFeedbackId(@PathVariable("feedbackId") int feedbackId) {
        String methodName = "getReportByFeedbackId()";
        logger.info(methodName + " called");
        return reportService.getReportByFeedBackId(feedbackId);
    }

    /**

     * @return This will return list of reports
     */
    @GetMapping
    @ApiOperation(value = "fetch all reports")
    public List<UserReportDto> getReports() {
        String methodName = "getAllReports()";
        logger.info(methodName + " called");

        return reportService.getReports();
    }

}
