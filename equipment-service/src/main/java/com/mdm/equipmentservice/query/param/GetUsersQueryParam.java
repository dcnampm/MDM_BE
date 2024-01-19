package com.mdm.equipmentservice.query.param;

import com.mdm.equipmentservice.model.entity.WorkingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetUsersQueryParam {

    private String keyword;

    private Boolean gender;

    private Long departmentId;

    private LocalDateTime birthdayQueryStart;

    private java.time.LocalDateTime birthdayQueryEnd;

    private Long[] roleIds;

    private String[] roleNames;

    private WorkingStatus workingStatus;

    private Boolean enabled;
}
