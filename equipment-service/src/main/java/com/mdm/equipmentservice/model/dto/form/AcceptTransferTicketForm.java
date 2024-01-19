package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.TransferTicket;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link TransferTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptTransferTicketForm implements Serializable {
    @NotNull(message = "{approvalDateMustNotBeNull}")
    private LocalDateTime approvalDate;

    private String approverNote;
    @NotNull(message = "{isApprovedMustNotBeNull}")
    private Boolean isApproved;
}