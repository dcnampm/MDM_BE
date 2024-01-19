package com.mdm.equipmentservice.model.dto.base;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.SupplyCategory}
 */
@Value
public class SupplyCategoryDto implements Serializable {

    Long id;

    String name;

    String alias;

    String note;
}