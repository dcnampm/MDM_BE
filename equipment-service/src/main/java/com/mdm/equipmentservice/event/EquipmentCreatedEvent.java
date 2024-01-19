package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.dto.base.EquipmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentCreatedEvent {
    private EquipmentDto equipmentDto;
}
