package com.mdm.equipmentservice.model.dto;

import com.mdm.equipmentservice.model.entity.Equipment;
import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.RiskLevel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Year;

/**
 * A DTO for the {@link Equipment} entity
 */
@Data
public class EquipmentExcelDto implements Serializable {
    private Long id;

    private String name;

    private String model;

    private String serial;

    private String code;

    private String hashCode;

    private String riskLevel;

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

    private String status;

    private String manufacturer;

    private String manufacturingCountry;

    private String categoryName;

    private String categoryGroupName;

    private String departmentName;

    private Integer regularMaintenance;

    private Integer regularInspection;

//    private Integer regularRadiationInspection;
//
//    private Integer regularExternalQualityAssessment;
//
//    private Integer regularClinicEnvironmentInspection;
//
//    private Integer regularCVRadiation;

    private LocalDateTime jointVentureContractExpirationDate;

    private LocalDateTime warrantyExpirationDate;

    private String projectName;

    private String supplierName;

    private String unitName;
}