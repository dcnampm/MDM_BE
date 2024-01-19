package com.mdm.equipmentservice.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "notification_configs")
@NamedEntityGraph(
        name = "notificationConfigWithRole", attributeNodes = {
        @NamedAttributeNode("role"),
}
)
@AllArgsConstructor
public class NotificationConfig { //config who can receive notification of a specific type

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "role_id", nullable = false)
    @ManyToOne(targetEntity = Role.class)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;
}
