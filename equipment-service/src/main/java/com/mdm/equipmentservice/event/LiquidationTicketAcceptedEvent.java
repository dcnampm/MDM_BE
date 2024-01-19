package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.entity.LiquidationTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiquidationTicketAcceptedEvent {
    private LiquidationTicket liquidationTicket;
}

