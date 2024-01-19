package com.mdm.equipmentservice.model.dto.event;

import com.mdm.equipmentservice.model.dto.fullinfo.ReportBrokenTicketFullInfoDto;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mdm.equipmentservice.event.ReportBrokenTicketAcceptedEvent} entity
 */
@Data
public class ReportBrokenTicketAcceptedEventDto implements Serializable {
    private final ReportBrokenTicketFullInfoDto reportBrokenTicket;
}