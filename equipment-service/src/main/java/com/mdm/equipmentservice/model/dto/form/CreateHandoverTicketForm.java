package com.mdm.equipmentservice.model.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.HandoverTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHandoverTicketForm implements Serializable {
    private Long responsiblePersonId;

    private LocalDateTime createdDate;

    private String creatorNote;

    private LocalDateTime handoverDate;

    private String handoverNote;

    private Long departmentId;
}