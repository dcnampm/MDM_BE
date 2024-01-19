package com.mdm.equipmentservice.model.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.Inventory}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto implements Serializable {
    private Long id;

    private LocalDateTime inventoryDate;

    private String note;

    private String inventoryPersonName;

    private String inventoryPersonUsername;

    private Integer time;
}