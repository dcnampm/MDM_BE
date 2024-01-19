package com.mdm.equipmentservice.model.dto.event;

import com.mdm.equipmentservice.event.HandoverTicketCreatedEvent;
import com.mdm.equipmentservice.model.dto.fullinfo.HandoverTicketFullInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link HandoverTicketCreatedEvent}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandoverTicketCreatedEventDto {
    private HandoverTicketFullInfoDto handoverTicket;
}
