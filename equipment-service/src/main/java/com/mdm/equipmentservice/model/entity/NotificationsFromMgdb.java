package com.mdm.equipmentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name="notifications_from_mgdb")
public class NotificationsFromMgdb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private Long equipmentId;

    private String content;

    private LocalDateTime createdAt;

    private Boolean isDeleted = false;

    private Long createdBy;

}
