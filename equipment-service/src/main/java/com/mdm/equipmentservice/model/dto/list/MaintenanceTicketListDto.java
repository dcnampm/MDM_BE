package com.mdm.equipmentservice.model.dto.list;

import com.mdm.equipmentservice.model.dto.base.EquipmentDto;
import com.mdm.equipmentservice.model.dto.base.FileStorageDto;
import com.mdm.equipmentservice.model.dto.base.UserDto;
import com.mdm.equipmentservice.model.entity.MaintenanceTicket;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link MaintenanceTicket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceTicketListDto implements Serializable {
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

    private LocalDateTime maintenanceDate;
    private LocalDateTime nextTime;

    private String maintenanceNote;

    private Double price;
    private List<FileStorageDto> attachments;
}