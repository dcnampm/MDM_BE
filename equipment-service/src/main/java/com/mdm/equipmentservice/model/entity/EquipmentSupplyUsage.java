package com.mdm.equipmentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "equipment_supply_usages")
public class EquipmentSupplyUsage {

    @ManyToOne(targetEntity = Equipment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "equipment_id")
    Equipment equipment;

    @JoinColumn(name = "supply_id")
    @ManyToOne(targetEntity = Supply.class, cascade = CascadeType.ALL)
    Supply supply;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(nullable = false)
    private Double amount;



}
