package com.mdm.equipmentservice.model.dto.base;

import com.mdm.equipmentservice.model.entity.TransferTicket;
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
public class TransferTicketDto implements Serializable {
    private Long id;

    private String fromDepartmentName;

    private String toDepartmentName;

    private LocalDateTime dateTransfer;


    private String note;

    private String approverName;

    private String approverUsername;
}