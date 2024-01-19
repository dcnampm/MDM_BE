package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.UpdateNotificationConfigForm;
import com.mdm.equipmentservice.model.dto.fullinfo.NotificationConfigFullInfoDto;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.NotificationConfigService;
import com.mdm.equipmentservice.util.MessageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/notification-configs")
public class NotificationConfigController {

    private final MessageUtil messageUtil;

    private final NotificationConfigService notificationConfigService;

    @Autowired
    public NotificationConfigController(MessageUtil messageUtil, NotificationConfigService notificationConfigService) {
        this.messageUtil = messageUtil;
        this.notificationConfigService = notificationConfigService;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<NotificationConfigFullInfoDto>>> getNotificationConfigs() {
        return ResponseEntity.ok(new GenericResponse<>(notificationConfigService.getNotificationConfigs(), 200));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<?>> updateNotificationConfig(
            @RequestBody Set<@Valid UpdateNotificationConfigForm> updateNotificationConfigForm) {
        notificationConfigService.updateNotificationConfig(updateNotificationConfigForm);
        return ResponseEntity.ok(new GenericResponse<>());
    }

    /*@GetMapping
    public ResponseEntity<GenericResponse<List<NotificationConfig>>> getNotificationConfigs(GetNotificationConfigsQueryParam getNotificationConfigsQueryParam) {
        List<NotificationConfig> notificationConfigs = notificationConfigService.getNotificationConfigs(getNotificationConfigsQueryParam);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(notificationConfigs, 200));
    }

    @GetMapping("/notification-types")
    public ResponseEntity<GenericResponse<List<String>>> getAllRoleThatCanReceiveNotification(NotificationType notificationType) {
        List<String> roles = notificationConfigService.getAllRoleThatCanReceiveNotification(notificationType);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(roles, 200));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<List<NotificationConfig>>> createNotificationConfigs(
           @RequestPart String role, @RequestPart List<NotificationType> notificationTypes) {
        List<NotificationConfig> notificationConfigs = notificationConfigService.createNotificationConfigs(role, notificationTypes);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(notificationConfigs, 201));
    }
    @PutMapping(value = "/{role}")
    public ResponseEntity<GenericResponse<List<NotificationConfig>>> updateNotificationConfigs(
            @PathVariable(value = "role") String role, @RequestBody List<NotificationType> notificationTypes) {
        List<NotificationConfig> notificationConfigs = notificationConfigService.updateNotificationConfigs(role, notificationTypes);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(notificationConfigs, 201));
    }*/

}
