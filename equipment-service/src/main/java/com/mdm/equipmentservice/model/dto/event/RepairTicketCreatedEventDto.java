package com.mdm.equipmentservice.model.dto.event;

import com.mdm.equipmentservice.model.dto.fullinfo.RepairTicketFullInfoDto;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mdm.equipmentservice.event.RepairTicketCreatedEvent} entity
 */
@Data
public class RepairTicketCreatedEventDto implements Serializable {
    private final RepairTicketFullInfoDto repairTicket;
}