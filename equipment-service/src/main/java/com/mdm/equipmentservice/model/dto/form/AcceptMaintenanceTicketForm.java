package com.mdm.equipmentservice.model.dto.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.MaintenanceTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptMaintenanceTicketForm implements Serializable {
    @NotNull
    private LocalDateTime approvalDate;

    private String approverNote;

    @NotNull
    private Boolean isApproved;
}