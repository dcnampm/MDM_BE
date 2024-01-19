package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.LiquidationTicket;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link LiquidationTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptLiquidationTicketForm implements Serializable {
    @NotNull(message = "{approvalDateMustNotBeNull}")
    private LocalDateTime approvalDate;

    private String approverNote;

    @NotNull
    private Boolean isApproved;
}