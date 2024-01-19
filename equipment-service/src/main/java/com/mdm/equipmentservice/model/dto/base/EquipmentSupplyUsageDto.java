package com.mdm.equipmentservice.model.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.EquipmentSupplyUsage}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentSupplyUsageDto implements Serializable {
    private EquipmentDto equipment;

    private SupplyDto supply;

    private Long id;

    private String note;

    private Double amount;
}