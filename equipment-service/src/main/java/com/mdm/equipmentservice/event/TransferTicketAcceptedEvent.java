package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.entity.TransferTicket;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferTicketAcceptedEvent {
    private final TransferTicket transferTicket;
}
