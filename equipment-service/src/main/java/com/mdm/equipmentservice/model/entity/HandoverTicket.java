package com.mdm.equipmentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.HashCodeExclude;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Table(name = "handover_tickets")
@Getter
@Setter
@ToString
public class HandoverTicket extends Ticket {

    @ManyToOne(targetEntity = Equipment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    @HashCodeExclude
    private Equipment equipment;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @ToString.Exclude
    @HashCodeExclude
    private Department department;

    @Column(nullable = false)
    private LocalDateTime handoverDate;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "responsible_person_id", referencedColumnName = "id")
    @ToString.Exclude
    @HashCodeExclude
    private User responsiblePerson;

    @Column(columnDefinition = "TEXT")
    private String handoverNote;

}
