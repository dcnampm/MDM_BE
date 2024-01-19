package com.mdm.equipmentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "report_broken_tickets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "code")
})
public class ReportBrokenTicket extends Ticket {

    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false)
    private Equipment equipment;

    @Column(columnDefinition = "TEXT")
    private String reason;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepairPriority priority;
}
