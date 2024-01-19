package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.dto.base.*;
import com.mdm.equipmentservice.model.entity.RiskLevel;
import com.mdm.equipmentservice.model.entity.SupplyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.Supply}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyFullInfoDto implements Serializable {

    private Long id;

    private String name;

    private String model;

    private String serial;

    private String hashCode;

    private Double amountUsed;

    private Double amount;

    private Date warehouseImportDate;

    private Integer yearOfManufacture;

    private Integer yearInUse;

    private Double importPrice;

    private List<EquipmentSupplyUsageDto> equipmentSupplyUsages;

    private ProjectDto project;

    private SupplyUnitDto unit;

    private SupplyCategoryDto category;

    private RiskLevel riskLevel;

    private String manufacturer;

    private String manufacturingCountry;

    private SupplierDto supplier;

    private Date expiryDate;

    private String technicalParameter;

    private String configuration;

    private String usageProcedure;

    private String note;
}