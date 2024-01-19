package com.mdm.equipmentservice.model.dto.event;

import com.mdm.equipmentservice.model.dto.fullinfo.TransferTicketFullInfoDto;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mdm.equipmentservice.event.TransferTicketCreatedEvent} entity
 */
@Data
public class TransferTicketCreatedEventDto implements Serializable {
    private final TransferTicketFullInfoDto transferTicket;
}