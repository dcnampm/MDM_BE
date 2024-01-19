package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.dto.base.*;
import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.Equipment}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentFullInfoDto implements Serializable {

    private Long id;

    private String name;

    private String model;

    private String serial;

    private String code;

    private String hashCode;

    private String qrCode;

    private RiskLevel riskLevel;

    private String technicalParameter;

    private LocalDateTime warehouseImportDate;

    private Year yearOfManufacture;

    private Year yearInUse;

    private String configuration;

    private Double importPrice;

    private Double initialValue;

    private Double annualDepreciation;

    private String usageProcedure;

    private String note;

    private EquipmentStatus status;

    private String manufacturer;

    private String manufacturingCountry;

    private EquipmentCategoryFullInfoDto category;

    private DepartmentFullInfoDto department;

    private Integer regularMaintenance;

    private Integer regularInspection;

    private LocalDateTime jointVentureContractExpirationDate;

    private LocalDateTime warrantyExpirationDate;

    private List<EquipmentSupplyUsageDto> equipmentSupplyUsages;

    private ProjectDto project;

    private List<InspectionTicketFullInfoDto> inspectionTickets;

    private SupplierFullInfoDto supplier;

    private List<HandoverTicketFullInfoDto> handoverTickets;

    private List<InventoryDto> inventories;

    private Boolean deleted = false;

    private List<FileStorageDto> attachments;

    private EquipmentUnitDto unit;

    private List<RepairTicketFullInfoDto> repairTickets;

    private List<ReportBrokenTicketFullInfoDto> reportBrokenTickets;

    private List<MaintenanceTicketFullInfoDto> maintenanceTickets;

    private List<TransferTicketFullInfoDto> transferTickets;
    private List<LiquidationTicketFullInfoDto> liquidationTickets;
}
