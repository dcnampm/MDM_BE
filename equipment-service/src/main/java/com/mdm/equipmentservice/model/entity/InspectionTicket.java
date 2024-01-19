package com.mdm.equipmentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "inspection_tickets")
public class InspectionTicket extends Ticket {

    @ManyToOne(targetEntity = Equipment.class)
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false)
    private Equipment equipment;

    private LocalDateTime inspectionDate;


    @Column(columnDefinition = "TEXT")
    private String inspectionNote;

    private Double price;
    @Enumerated(EnumType.STRING)
    private InspectionEvaluationStatus evaluationStatus;

    @ManyToOne
    @JoinColumn(name = "inspection_company_id", referencedColumnName = "id")
    private Supplier inspectionCompany;

    public LocalDateTime getNextTime() {
        return ObjectUtils.isNotEmpty(inspectionDate) ? inspectionDate.plusMonths(equipment.getRegularInspection()) : null;
    }
}
