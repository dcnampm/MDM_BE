package com.mdm.equipmentservice.model.dto.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.RepairTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRepairTicketForm implements Serializable {
    @NotNull(message = "{createdDateMustNotBeNull}")
    private LocalDateTime createdDate;

    private String creatorNote;

    @NotNull(message = "{estimatedCostMustNotBeNull}")
    private Long estimatedCost;
    @NotNull(message = "{repairStartDateMustNotBeNull}")
    private LocalDateTime repairStartDate;

    private Long repairCompanyId;
}