package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.form.UpdateNotificationConfigForm;
import com.mdm.equipmentservice.model.dto.fullinfo.NotificationConfigFullInfoDto;
import com.mdm.equipmentservice.model.entity.NotificationConfig;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,uses = {IdToEntityMapper.class})
public abstract class NotificationConfigMapper {

    public abstract NotificationConfigFullInfoDto toFullInfoDto(NotificationConfig notificationConfig);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "roleId")
    public abstract NotificationConfig toEntity(UpdateNotificationConfigForm updateNotificationConfigForm);

    public abstract Set<NotificationConfig> toEntities(Set<UpdateNotificationConfigForm> updateNotificationConfigForms);
}
