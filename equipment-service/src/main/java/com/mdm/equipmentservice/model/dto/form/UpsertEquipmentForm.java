package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.RiskLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Year;

/**
 * A DTO for the {@link com.mdm.equipmentservice.model.entity.Equipment} entity
 */
@Data
public class UpsertEquipmentForm implements Serializable {

    @NotBlank(message = "Tên thiết bị không được để trống")
    private String name;

    @NotBlank(message = "Model không được để trống")
    private String model;

    @NotBlank(message = "Serial không được để trống")
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

    private Long categoryId;

    private Long departmentId;

    private Integer regularMaintenance = 0;

    private Integer regularInspection = 0;

    private LocalDateTime jointVentureContractExpirationDate;

    private LocalDateTime warrantyExpirationDate;

    private Long projectId;

    private Long supplierId;

    private Boolean deleted = false;

    private Long unitId;

}