package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.entity.ReportBrokenTicket;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportBrokenTicketAcceptedEvent {
    private ReportBrokenTicket reportBrokenTicket;
}
