package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.RiskLevel;
import com.mdm.equipmentservice.model.entity.SupplyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the {@link com.mdm.equipmentservice.model.entity.Supply} entity
 */
@Data
public class UpsertSupplyForm implements Serializable {

    @NotBlank(message = "{supplyNameMustNotBeBlank}")
    private final String name;

    @NotBlank(message = "{supplyModelMustNotBeBlank}")
    private final String model;

    @NotBlank(message = "{supplySerialMustNotBeBlank}")
    private final String serial;

    @NotNull(message = "{supplyAmountMustNotBeNull}")
    private final Double amount;

    private final int yearOfManufacture;

    private final int yearInUse;

    private final Double importPrice;

    private final SupplyStatus status;

    private final String manufacturer;

    private final String manufacturingCountry;

    private final Date expiryDate;

    private final String technicalParameter;

    private final String configuration;

    private final String usageProcedure;

    private final String note;

    private final String hashCode;

    private final Double amountUsed;

    private final Date warehouseImportDate;

    private final Long unitId;

    private final Long supplierId;

    private final Long projectId;

    private final Long categoryId;

    private final RiskLevel riskLevel;
}