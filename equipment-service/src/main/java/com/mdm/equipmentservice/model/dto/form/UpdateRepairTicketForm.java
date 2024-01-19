package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.RepairStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.RepairTicket}
 */
@Value
public class UpdateRepairTicketForm implements Serializable {
    @NotNull(message = "{repairStatusMustNotBeNull}")
    RepairStatus repairStatus;

    @NotNull(message = "{repairEndDateMustNotBeNull}")
    LocalDateTime repairEndDate;

    @NotNull(message = "{actualCostMustNotBeNull}")
    Long actualCost;

    String repairNote;
}