package com.mdm.equipmentservice.model.dto.event;

import com.mdm.equipmentservice.model.dto.fullinfo.MaintenanceTicketFullInfoDto;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mdm.equipmentservice.event.MaintenanceTicketUpdatedEvent} entity
 */
@Data
public class MaintenanceTicketUpdatedEventDto implements Serializable {
    private final MaintenanceTicketFullInfoDto maintenanceTicket;
}