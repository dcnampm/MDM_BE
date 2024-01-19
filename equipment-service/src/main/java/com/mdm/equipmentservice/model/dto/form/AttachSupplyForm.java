package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * A DTO for the {@link com.mdm.equipmentservice.model.entity.EquipmentSupplyUsage} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachSupplyForm implements Serializable {

    private Long supplyId;

    private Double amount;

    private String note;

    private Long equipmentId;

    private Double amountUsed;


}
