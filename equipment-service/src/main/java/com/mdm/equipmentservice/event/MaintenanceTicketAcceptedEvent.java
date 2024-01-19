package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.entity.MaintenanceTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceTicketAcceptedEvent {
    private MaintenanceTicket maintenanceTicket;
}
