package com.mdm.equipmentservice.model.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.SupplyCategory}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertSupplyCategoryForm implements Serializable {

    private Long id;

    private String name;

    private String alias;

    private String note;
}