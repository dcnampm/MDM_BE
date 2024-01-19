package com.mdm.equipmentservice.model.dto.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptHandoverTicketForm {
    private String approverNote;
    @NotNull(message = "{handoverAcceptStatusIsRequired}")
    private Boolean isApproved;
    private LocalDateTime approvalDate;
}
