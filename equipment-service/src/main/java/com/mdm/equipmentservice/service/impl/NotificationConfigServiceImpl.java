package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.mapper.NotificationConfigMapper;
import com.mdm.equipmentservice.model.dto.form.UpdateNotificationConfigForm;
import com.mdm.equipmentservice.model.dto.fullinfo.NotificationConfigFullInfoDto;
import com.mdm.equipmentservice.model.entity.NotificationConfig;
import com.mdm.equipmentservice.model.entity.NotificationType;
import com.mdm.equipmentservice.model.repository.NotificationConfigRepository;
import com.mdm.equipmentservice.service.NotificationConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class NotificationConfigServiceImpl implements NotificationConfigService {

    private final NotificationConfigMapper notificationConfigMapper;

    private final NotificationConfigRepository notificationConfigRepository;

    @Autowired
    public NotificationConfigServiceImpl(NotificationConfigMapper notificationConfigMapper, NotificationConfigRepository notificationConfigRepository) {
        this.notificationConfigMapper = notificationConfigMapper;
        this.notificationConfigRepository = notificationConfigRepository;
    }

    /**
     * Get all roles that can receive a specific notification type
     *
     * @param notificationType notification type to be checked
     * @return list of roles that can receive the notification type
     */
    @Override
    public List<String> getAllRoleThatCanReceiveNotification(NotificationType notificationType) {
        return notificationConfigRepository.findByNotificationType(notificationType)
                .stream()
                .map(notificationConfig -> notificationConfig.getRole().getName())
                .toList();
    }


    @Override
    public void updateNotificationConfig(Set<UpdateNotificationConfigForm> updateNotificationConfigForms) {
        /*Set<NotificationConfig> notificationConfigs = new HashSet<>();
        Arrays.stream(NotificationType.values()).forEach(notificationType -> {
            Map<NotificationType, Set<Long>> notificationConfig = updateNotificationConfigForms.getNotificationConfig();
            notificationConfig.get(notificationType).forEach(roleId -> {
                Role role = new Role();
                role.setId(roleId);
                notificationConfigs.add(new NotificationConfig(null, role, notificationType));
            });
        });*/
        notificationConfigRepository.deleteAll();
        List<NotificationConfig> notificationConfigs = updateNotificationConfigForms.stream().map(notificationConfigMapper::toEntity).toList();
        notificationConfigs = notificationConfigRepository.saveAll(notificationConfigs);
        ;
    }

    @Override
    public List<NotificationConfigFullInfoDto> getNotificationConfigs() {
        return notificationConfigRepository.findAll().stream().map(notificationConfigMapper::toFullInfoDto).toList();
    }
}
