package com.mdm.equipmentservice.model.dto.base;

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
public class EquipmentDto implements Serializable {
    private final Long id;

    private final String name;

    private final String model;

    private final String serial;

    private final String code;

    private final String hashCode;

    private final String technicalParameter;

    private final LocalDateTime warehouseImportDate;

    private final Year yearOfManufacture;

    private final Year yearInUse;

    private final String configuration;

    private final Double importPrice;

    private final Double initialValue;

    private final Double annualDepreciation;

    private final String usageProcedure;

    private final String note;

    private final EquipmentStatus status;

    private final String manufacturer;

    private final String manufacturingCountry;

    private final Long supplierId;

    private final Long departmentId;

    private final Long projectId;


    private final RiskLevel riskLevel;

    private final Long categoryId;

    private Integer regularMaintenance;

    private Integer regularInspection;

    private LocalDateTime jointVentureContractExpirationDate;

    private LocalDateTime warrantyExpirationDate;
}