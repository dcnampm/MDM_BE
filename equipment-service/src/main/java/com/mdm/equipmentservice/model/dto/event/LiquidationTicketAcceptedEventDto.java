package com.mdm.equipmentservice.model.dto.event;

import com.mdm.equipmentservice.model.dto.fullinfo.LiquidationTicketFullInfoDto;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mdm.equipmentservice.event.LiquidationTicketAcceptedEvent} entity
 */
@Data
public class LiquidationTicketAcceptedEventDto implements Serializable {
    private final LiquidationTicketFullInfoDto liquidationTicket;
}