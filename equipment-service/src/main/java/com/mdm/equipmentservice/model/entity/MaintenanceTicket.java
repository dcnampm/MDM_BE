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
@Table(name = "maintenance_tickets")
public class MaintenanceTicket extends Ticket {
    @ManyToOne(targetEntity = Equipment.class)
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false)
    private Equipment equipment;

    private LocalDateTime maintenanceDate;

    @Column(columnDefinition = "TEXT")
    private String maintenanceNote;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "maintenance_company_id", referencedColumnName = "id")
    private Supplier maintenanceCompany;

    public LocalDateTime getNextTime() {
        if (maintenanceDate == null) {
            return null;
        }
        return maintenanceDate.plusMonths(equipment.getRegularMaintenance());
    }
}
