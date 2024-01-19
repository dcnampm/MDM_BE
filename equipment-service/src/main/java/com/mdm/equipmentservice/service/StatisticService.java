package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.base.StatisticDashboardDto;

import java.util.List;

public interface StatisticService {

    StatisticDashboardDto getStatisticDashboard();

    List<StatisticDashboardDto.CountByGroupAndCategory> getStatisticByEquipmentGroup();

    List<StatisticDashboardDto.CountByDepartmentAndStatus> getStatisticByDepartmentAndStatus();
}
