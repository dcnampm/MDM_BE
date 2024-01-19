package com.mdm.equipmentservice.model.dto.form;


import com.mdm.equipmentservice.model.entity.InspectionTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * DTO for {@link InspectionTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInspectionTicketForm {
    private LocalDateTime createdDate;

    private String creatorNote;
}
