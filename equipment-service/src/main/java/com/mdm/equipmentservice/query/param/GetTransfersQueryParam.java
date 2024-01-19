package com.mdm.equipmentservice.query.param;

import com.mdm.equipmentservice.model.entity.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTransfersQueryParam {
    private String keyword; //search equipment

    private TicketStatus status;
}
