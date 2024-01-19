package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.base.StatisticDashboardDto;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/statistics")
public class StatisticController {
    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("dashboard")
    public ResponseEntity<GenericResponse<StatisticDashboardDto>> statisticDashboard() {
        StatisticDashboardDto statisticDashboard = statisticService.getStatisticDashboard();
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(statisticDashboard));
    }


    @GetMapping("/equipments/groups")
    public ResponseEntity<GenericResponse<List<StatisticDashboardDto.CountByGroupAndCategory>>> statisticEquipmentByGroup() {
        List<StatisticDashboardDto.CountByGroupAndCategory> count = statisticService.getStatisticByEquipmentGroup();
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(count));
    }

    @GetMapping("/equipments/departments/statuses")
    public ResponseEntity<GenericResponse<List<StatisticDashboardDto.CountByDepartmentAndStatus>>> statisticEquipmentByDepartmentAndStatus() {
        List<StatisticDashboardDto.CountByDepartmentAndStatus> count = statisticService.getStatisticByDepartmentAndStatus();
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(count));
    }
}
