package com.mdm.equipmentservice.model.dto.base;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mdm.equipmentservice.model.entity.SupplyUnit} entity
 */
@Data
public class SupplyUnitDto implements Serializable {
    private final Long id;
    private final String name;
}