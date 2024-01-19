package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.InspectionEvaluationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.InspectionTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInspectionTicketForm {
    @NotNull
    private LocalDateTime inspectionDate;

    private String inspectionNote;

    private Long inspectionCompanyId;

    @NotNull
    private Double price;

    @NotNull
    private InspectionEvaluationStatus evaluationStatus;
}
