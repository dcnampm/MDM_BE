package com.mdm.equipmentservice.model.dto.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptReportBrokenTicketForm {

    private String approverNote;

    @NotNull
    private Boolean isApproved;

    private LocalDateTime approvalDate;

}
