package com.mdm.equipmentservice.model.dto.event;

import com.mdm.equipmentservice.model.dto.fullinfo.InspectionTicketFullInfoDto;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mdm.equipmentservice.event.InspectionTicketAcceptedEvent} entity
 */
@Data
public class InspectionTicketAcceptedEventDto implements Serializable {
    private final InspectionTicketFullInfoDto inspectionTicket;
}