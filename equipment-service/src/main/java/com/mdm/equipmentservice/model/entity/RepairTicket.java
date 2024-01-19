package com.mdm.equipmentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(
        name = "repair_tickets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "code")
}
)
@ToString
public class RepairTicket extends Ticket {

    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false)
    private Equipment equipment;

    private Long estimatedCost;

    @Enumerated(EnumType.STRING)
    private RepairStatus repairStatus;

    private LocalDateTime repairStartDate;

    private LocalDateTime repairEndDate;

    private Long actualCost;

    @ManyToOne
    @JoinColumn(name = "repair_company_id", referencedColumnName = "id")
    private Supplier repairCompany;

    private String repairNote;

    //Người nghiệm thu sửa chữa
    @ManyToOne
    @JoinColumn(name = "acceptance_tester_id", referencedColumnName = "id")
    private User acceptanceTester;

    private LocalDateTime acceptanceTestDate; //Ngày nghiệm thu sửa chữa
}
