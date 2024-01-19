package com.mdm.equipmentservice.model.dto.event;

import com.mdm.equipmentservice.model.dto.fullinfo.InspectionTicketFullInfoDto;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mdm.equipmentservice.event.InspectionTicketUpdatedEvent} entity
 */
@Data
public class InspectionTicketUpdatedEventDto implements Serializable {
    private final InspectionTicketFullInfoDto inspectionTicket;
}