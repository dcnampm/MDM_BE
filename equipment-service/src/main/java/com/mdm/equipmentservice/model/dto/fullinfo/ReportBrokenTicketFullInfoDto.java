package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.dto.base.EquipmentDto;
import com.mdm.equipmentservice.model.dto.base.FileStorageDto;
import com.mdm.equipmentservice.model.dto.base.UserDto;
import com.mdm.equipmentservice.model.entity.RepairPriority;
import com.mdm.equipmentservice.model.entity.ReportBrokenTicket;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link ReportBrokenTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportBrokenTicketFullInfoDto implements Serializable {
    private Long id;

    private String code;

    private UserDto creator;

    private LocalDateTime createdDate;

    private String creatorNote;

    private LocalDateTime approvalDate;

    private String approverNote;

    private UserDto approver;

    private TicketStatus status;

    private EquipmentDto equipment;

    private String reason;

    private RepairPriority priority;
    private List<FileStorageDto> attachments;
}