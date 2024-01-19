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
@Table(name = "transfer_tickets")
public class TransferTicket extends Ticket {
    @ManyToOne(targetEntity = Equipment.class)
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false)
    private Equipment equipment;

    @JoinColumn(nullable = false, name = "from_department_id")
    @ManyToOne
    private Department fromDepartment;

    @JoinColumn(nullable = false, name = "to_department_id")
    @ManyToOne(targetEntity = Department.class)
    private Department toDepartment;

    @Column(nullable = false)
    private LocalDateTime dateTransfer;

    @Column(columnDefinition = "TEXT")
    private String transferNote;


}
