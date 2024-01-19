package com.mdm.equipmentservice.model.dto.base;

import com.mdm.equipmentservice.model.entity.MaintenanceTicket;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link MaintenanceTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceTicketDto implements Serializable {
    private Long id;

    private LocalDateTime maintenanceDate;

    private LocalDateTime nextTime;

    private String maintenanceNote;

    private Double price;

    private String code;

    private LocalDateTime createdDate;

    private String creatorNote;

    private LocalDateTime approvalDate;

    private String approverNote;

    private TicketStatus status;
}