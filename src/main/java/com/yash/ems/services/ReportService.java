package com.yash.ems.services;

import com.yash.ems.dto.UserReportDto;

import java.util.List;

public interface ReportService {

    UserReportDto getReportByFeedBackId(int userId);

    List<UserReportDto> getReports();
}
