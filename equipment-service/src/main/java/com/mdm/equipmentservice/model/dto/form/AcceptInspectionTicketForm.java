package com.mdm.equipmentservice.model.dto.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.InspectionTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptInspectionTicketForm {
    @NotNull
    private LocalDateTime approvalDate;

    private String approverNote;

    @NotNull
    private Boolean isApproved;
}
