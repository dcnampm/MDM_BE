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
@Table(name = "liquidation_tickets")
public class LiquidationTicket extends Ticket {

    @Column(nullable = false)
    private LocalDateTime liquidationDate;

    @ManyToOne(targetEntity = Equipment.class)
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false)
    private Equipment equipment;

    @Column(columnDefinition = "TEXT")
    private String liquidationNote;

    private Double price;
}
