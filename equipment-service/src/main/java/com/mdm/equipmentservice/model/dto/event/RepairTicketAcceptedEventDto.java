package com.mdm.equipmentservice.model.dto.event;

import com.mdm.equipmentservice.model.dto.fullinfo.RepairTicketFullInfoDto;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mdm.equipmentservice.event.RepairTicketAcceptedEvent} entity
 */
@Data
public class RepairTicketAcceptedEventDto implements Serializable {
    private final RepairTicketFullInfoDto repairTicket;
}