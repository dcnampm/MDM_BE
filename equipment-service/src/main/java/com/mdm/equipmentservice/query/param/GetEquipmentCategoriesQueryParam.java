package com.mdm.equipmentservice.query.param;

import lombok.Data;

@Data
public class GetEquipmentCategoriesQueryParam {
    private String keyword;

    private Long groupId;
}
