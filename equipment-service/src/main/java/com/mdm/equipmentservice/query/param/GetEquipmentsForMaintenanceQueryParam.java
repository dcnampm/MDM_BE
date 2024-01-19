package com.mdm.equipmentservice.query.param;

import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEquipmentsForMaintenanceQueryParam {
    private String keyword;

    private EquipmentStatus equipmentStatus;

    private Long departmentId;

    private Long categoryId;

    private Long groupId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastTimeFrom;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastTimeTo;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime nextTimeFrom;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime nextTimeTo;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Integer regularMaintenance;
}
