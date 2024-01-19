package com.mdm.equipmentservice.query.param;

import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEquipmentsForRepairQueryParam {

    private String keyword;

    private EquipmentStatus equipmentStatus;

    private Long departmentId;

    private Long categoryId;

    private Long groupId;
}
