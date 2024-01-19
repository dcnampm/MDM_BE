package com.mdm.equipmentservice.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Equipment.class)
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false)
    private Equipment equipment;


    @Column(nullable = false)
    private LocalDateTime inventoryDate;

    @Column(columnDefinition = "TEXT")
    private String note;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "inventory_person_id", referencedColumnName = "id", nullable = false)
    private User inventoryPerson;

    @Column(nullable = false)
    private Integer time;

}
