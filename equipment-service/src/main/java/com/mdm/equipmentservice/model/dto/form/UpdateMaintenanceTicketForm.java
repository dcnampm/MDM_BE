package com.mdm.equipmentservice.model.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.MaintenanceTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMaintenanceTicketForm implements Serializable {
    private LocalDateTime maintenanceDate;

    private String maintenanceNote;

    private Long maintenanceCompanyId;

    private Double price;
}