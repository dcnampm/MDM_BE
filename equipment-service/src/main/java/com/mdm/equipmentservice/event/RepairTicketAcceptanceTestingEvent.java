package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.entity.RepairTicket;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Nghiệm thu sửa chữa
 */
@Data
@AllArgsConstructor
public class RepairTicketAcceptanceTestingEvent {
    private RepairTicket repairTicket;
}
