package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.TransferTicket;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link TransferTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransferTicketForm implements Serializable {
    @NotNull(message = "{createdDateMustNotBeNull}")
    private LocalDateTime createdDate;

    private String creatorNote;

    @NotNull(message = "{toDepartmentMustNotBeNull}")
    private Long toDepartmentId;

    @NotNull(message = "{dateTransferMustNotBeNull}")
    private LocalDateTime dateTransfer;

    private String transferNote;
}