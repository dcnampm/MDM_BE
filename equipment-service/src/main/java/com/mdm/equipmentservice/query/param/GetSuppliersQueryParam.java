package com.mdm.equipmentservice.query.param;

import lombok.Data;

import java.util.List;

@Data
public class GetSuppliersQueryParam {
    private String keyword;
    private Long serviceId;
}
