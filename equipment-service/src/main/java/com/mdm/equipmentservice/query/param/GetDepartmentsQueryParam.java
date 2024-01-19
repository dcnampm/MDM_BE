package com.mdm.equipmentservice.query.param;


import com.mdm.equipmentservice.model.entity.DepartmentActiveStatus;
import lombok.Data;

@Data
public class GetDepartmentsQueryParam {
    private String keyword;

    private DepartmentActiveStatus activeStatus;

    private Long contactPersonId;

    private Long headOfDepartmentId;

    private Long chiefNurseId;

    private Long managerId;
}
