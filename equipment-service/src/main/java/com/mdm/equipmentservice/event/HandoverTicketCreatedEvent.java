package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.entity.HandoverTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandoverTicketCreatedEvent {
    private HandoverTicket handoverTicket;
}
