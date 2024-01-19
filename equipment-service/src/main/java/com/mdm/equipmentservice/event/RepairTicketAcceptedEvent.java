package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.entity.RepairTicket;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RepairTicketAcceptedEvent {
    private RepairTicket repairTicket;

}
