package com.mdm.equipmentservice.model.dto.base;

import com.mdm.equipmentservice.model.entity.HandoverStatus;
import com.mdm.equipmentservice.model.entity.HandoverTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link HandoverTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandoverDto implements Serializable {

    Long id;

    LocalDateTime handoverDate;

    UserDto responsiblePerson;

    String handoverNote;

    DepartmentDto department;

    UserDto createdBy;

    UserDto approver;

    LocalDateTime approvalDate;

    HandoverStatus status;

    String approverNote;
}