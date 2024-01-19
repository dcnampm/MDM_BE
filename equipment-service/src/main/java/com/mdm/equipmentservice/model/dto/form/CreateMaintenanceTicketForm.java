package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.MaintenanceTicket;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link MaintenanceTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMaintenanceTicketForm implements Serializable {
    @NotNull
    private LocalDateTime createdDate;
    private String creatorNote;
}