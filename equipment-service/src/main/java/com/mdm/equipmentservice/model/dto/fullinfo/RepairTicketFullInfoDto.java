package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.dto.base.*;
import com.mdm.equipmentservice.model.entity.RepairStatus;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.RepairTicket}
 */
@Value
public class RepairTicketFullInfoDto implements Serializable {

    Long id;

    String code;

    UserDto creator;

    LocalDateTime createdDate;

    String creatorNote;

    LocalDateTime approvalDate;

    String approverNote;

    UserDto approver;

    TicketStatus status;

    EquipmentDto equipment;

    Long estimatedCost;

    RepairStatus repairStatus;

    LocalDateTime repairStartDate;

    LocalDateTime repairEndDate;

    Long actualCost;

    SupplierDto repairCompany;

    List<FileStorageDto> attachments;
}