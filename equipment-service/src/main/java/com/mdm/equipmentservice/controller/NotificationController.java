package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.entity.NotificationsFromMgdb;
import com.mdm.equipmentservice.query.param.GetNotificationQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {


    private final NotificationService notificationService;

    private final PagedResourcesAssembler<NotificationsFromMgdb> notificationPagedResourcesAssembler;

    @Autowired
    public NotificationController(NotificationService notificationService, PagedResourcesAssembler<NotificationsFromMgdb> notificationPagedResourcesAssembler) {
        this.notificationService = notificationService;
        this.notificationPagedResourcesAssembler = notificationPagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<NotificationsFromMgdb>>>> getAllNotification(
            GetNotificationQueryParam getNotificationQueryParam,
            Pageable pageable) {
        PagedModel<EntityModel<NotificationsFromMgdb>> entityModels;
        entityModels = notificationPagedResourcesAssembler.toModel(notificationService.getAllNotification(getNotificationQueryParam, pageable));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteNotification(@PathVariable(name = "id") Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(new GenericResponse<>(null));
    }



//
//    @DeleteMapping(value = "/delete-multiple")
//    public ResponseEntity<GenericResponse<Object>> deleteMultiple(@RequestBody List<String> notificationIds) {
//        notificationService.deleteMultipleNotification(notificationIds);
//        return new ResponseEntity<>(new GenericResponse<>(null, 204), HttpStatus.NO_CONTENT);
//    }
}
