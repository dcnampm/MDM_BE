package com.mdm.equipmentservice.model.dto.list;

import com.mdm.equipmentservice.model.dto.base.DepartmentDto;
import com.mdm.equipmentservice.model.dto.base.EquipmentCategoryDto;
import com.mdm.equipmentservice.model.dto.fullinfo.TransferTicketFullInfoDto;
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
public class EquipmentListTransferDto implements Serializable {
    private Long id;
    private String name;
    private String model;
    private String serial;
    private String code;
    private String hashCode;
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
    private EquipmentCategoryDto category;
    private DepartmentDto department;
    private Integer regularMaintenance;
    private Integer regularInspection;
    private LocalDateTime jointVentureContractExpirationDate;
    private LocalDateTime warrantyExpirationDate;
    private List<TransferTicketFullInfoDto> transferTickets;
    private Boolean deleted = false;
}