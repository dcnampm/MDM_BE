package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.entity.HandoverTicket;
import lombok.Data;

@Data
public class HandoverTicketAcceptedEvent {
    private HandoverTicket handoverTicket;

    public HandoverTicketAcceptedEvent(HandoverTicket handoverTicket) {
        this.handoverTicket = handoverTicket;
    }
}
