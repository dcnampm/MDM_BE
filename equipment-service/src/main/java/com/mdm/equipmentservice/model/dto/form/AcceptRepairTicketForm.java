package com.mdm.equipmentservice.model.dto.form;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.RepairTicket}
 */
@Value
public class AcceptRepairTicketForm implements Serializable {
    @NotNull(message = "{approvalDateMustNotBeNull}")
    LocalDateTime approvalDate;

    String approverNote;
    @NotNull(message = "{isAcceptedMustNotBeNull}")
    Boolean isAccepted;
}