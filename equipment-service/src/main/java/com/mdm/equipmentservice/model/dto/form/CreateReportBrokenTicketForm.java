package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.RepairPriority;
import com.mdm.equipmentservice.model.entity.ReportBrokenTicket;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link ReportBrokenTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportBrokenTicketForm implements Serializable {
    @NotNull(message = "{createdDateMustNotBeNull}")
    private LocalDateTime createdDate;

    private String creatorNote;

    private String reason;

    @NotNull(message = "{priorityMustNotBeNull}")
    private RepairPriority priority;


}