package com.mdm.equipmentservice.model.dto.base;

import com.mdm.equipmentservice.model.entity.RepairPriority;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.ReportBrokenTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportBrokenTicketDto implements Serializable {
    private Long id;

    private String code;

    private LocalDateTime createdDate;

    private String creatorNote;

    private LocalDateTime approvalDate;

    private String approverNote;

    private TicketStatus status;

    private String reason;

    private RepairPriority priority;
}