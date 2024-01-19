package com.mdm.equipmentservice.query.param;

import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.RiskLevel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.Year;

@Data
public class GetEquipmentsQueryParam {

    private String keyword;

    private EquipmentStatus status;

    private RiskLevel riskLevel;

    private LocalDateTime warehouseImportDateFrom;

    private LocalDateTime warehouseImportDateTo;


    private Year yearOfManufactureFrom;

    private Year yearOfManufactureTo;

    private Year yearInUseFrom;

    private Year yearInUseTo;

    private Double importPriceFrom;

    private Double importPriceTo;

    private Double initialValueFrom;

    private Double initialValueTo;

    private Long supplierId;

    private Long categoryId;

    private Long departmentId;

    private Long projectId;

    private Long groupId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastMaintenanceDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastMaintenanceDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime nextMaintenanceDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime nextMaintenanceDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastInspectionDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastInspectionDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime nextInspectionDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime nextInspectionDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime jointVentureContractExpirationDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime jointVentureContractExpirationDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime warrantyExpirationDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime warrantyExpirationDateTo;

    private Integer regularMaintenance;

    private Integer regularInspection;
}
