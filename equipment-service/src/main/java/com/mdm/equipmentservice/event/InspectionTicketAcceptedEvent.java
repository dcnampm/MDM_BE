package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.entity.InspectionTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionTicketAcceptedEvent {
    private InspectionTicket inspectionTicket;
}
