package com.mdm.equipmentservice.model.dto;

import com.mdm.equipmentservice.model.entity.InspectionEvaluationStatus;
import com.mdm.equipmentservice.model.entity.InspectionTicket;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link InspectionTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionTicketDto implements Serializable {
    private Long id;

    private String code;

    private LocalDateTime createdDate;

    private String creatorNote;

    private LocalDateTime approvalDate;

    private String approverNote;

    private TicketStatus status;

    private LocalDateTime inspectionDate;

    private LocalDateTime nextTime;

    private String inspectionNote;

    private Double price;

    private InspectionEvaluationStatus evaluationStatus;
}