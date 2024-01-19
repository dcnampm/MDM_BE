package com.mdm.equipmentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(
        name = "equipments",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"serial"}, name = "serial"), @UniqueConstraint(columnNames = {"code"}, name = "code")}
)
@NamedEntityGraph(
        name = "equipmentFullInfoDto", subgraphs = {
        @NamedSubgraph(
                name = "handoverTickets-subgraph",
                attributeNodes = {@NamedAttributeNode("responsiblePerson"), @NamedAttributeNode("equipment"), @NamedAttributeNode("department")}
        ),
        @NamedSubgraph(
                name = "inspectionTicket-subgraph", attributeNodes = {@NamedAttributeNode("equipment"), @NamedAttributeNode("inspectionCompany")}
        ),
        @NamedSubgraph(
                name = "maintenanceTickets-subgraph", attributeNodes = {@NamedAttributeNode("equipment"), @NamedAttributeNode("maintenanceCompany")}
        ),
        @NamedSubgraph(
                name = "repairTickets-subgraph",
                attributeNodes = {@NamedAttributeNode("equipment"), @NamedAttributeNode("acceptanceTester"), @NamedAttributeNode("repairCompany")}
        ),
        @NamedSubgraph(
                name = "transferTickets-subgraph",
                attributeNodes = {@NamedAttributeNode("equipment"), @NamedAttributeNode("fromDepartment"), @NamedAttributeNode("toDepartment")}
        ),
        @NamedSubgraph(
                name = "reportBrokenTickets-subgraph", attributeNodes = {@NamedAttributeNode("equipment")}
        )
}, attributeNodes = {
        @NamedAttributeNode(value = "handoverTickets", subgraph = "handoverTickets-subgraph"),
        @NamedAttributeNode("category"),
        @NamedAttributeNode("department"),
        @NamedAttributeNode(value = "inspectionTickets", subgraph = "inspectionTicket-subgraph"),
        @NamedAttributeNode(value = "maintenanceTickets", subgraph = "maintenanceTickets-subgraph"),
        @NamedAttributeNode("inventories"),
        @NamedAttributeNode("project"),
        @NamedAttributeNode("supplier"),
        @NamedAttributeNode(value = "reportBrokenTickets", subgraph = "reportBrokenTickets-subgraph"),
        @NamedAttributeNode(value = "repairTickets", subgraph = "repairTickets-subgraph"),
        @NamedAttributeNode(value = "transferTickets", subgraph = "transferTickets-subgraph"),
        @NamedAttributeNode("equipmentSupplyUsages"),
}
)
public class Equipment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String serial;

    private String code;

    private String hashCode;

    @Column(columnDefinition = "TEXT")
    private String qrCode;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    @Column(columnDefinition = "TEXT")
    private String technicalParameter;

    private LocalDateTime warehouseImportDate;

    private Year yearOfManufacture;

    private Year yearInUse;

    @Column(columnDefinition = "TEXT")
    private String configuration;

    private Double importPrice;

    private Double initialValue;

    private Double annualDepreciation;

    @Column(columnDefinition = "TEXT")
    private String usageProcedure;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status;

    private String manufacturer;

    private String manufacturingCountry;

    @ManyToOne(targetEntity = EquipmentCategory.class)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "category"))
    private EquipmentCategory category;

    @JoinColumn(name = "department_id")
    @ManyToOne(targetEntity = Department.class)
    private Department department;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer regularMaintenance;

    private LocalDateTime lastMaintenanceDate;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer regularInspection;

    private LocalDateTime lastInspectionDate;

    private LocalDateTime jointVentureContractExpirationDate;

    private LocalDateTime warrantyExpirationDate;

    @OneToMany(mappedBy = "equipment", targetEntity = EquipmentSupplyUsage.class)
    @ToString.Exclude
    private List<EquipmentSupplyUsage> equipmentSupplyUsages;


    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id", foreignKey = @ForeignKey(name = "project"))
    private Project project;

    @OneToMany(mappedBy = "equipment", targetEntity = InspectionTicket.class, orphanRemoval = true)
    @ToString.Exclude
    private List<InspectionTicket> inspectionTickets;

    @OneToMany(mappedBy = "equipment", targetEntity = MaintenanceTicket.class, orphanRemoval = true)
    @ToString.Exclude
    private List<MaintenanceTicket> maintenanceTickets;

    @OneToMany(mappedBy = "equipment", targetEntity = TransferTicket.class, orphanRemoval = true)
    @ToString.Exclude
    private List<TransferTicket> transferTickets;

    @ManyToOne(targetEntity = Supplier.class)
    @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "supplier"))
    @ToString.Exclude
    private Supplier supplier;

    @OneToMany(targetEntity = HandoverTicket.class, mappedBy = "equipment", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<HandoverTicket> handoverTickets;

    @OneToMany(mappedBy = "equipment", targetEntity = RepairTicket.class, cascade = CascadeType.ALL)
    @ToString.Exclude
    @Fetch(value = FetchMode.SUBSELECT)
    private List<RepairTicket> repairTickets;

    @OneToMany(mappedBy = "equipment", targetEntity = ReportBrokenTicket.class, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @ToString.Exclude
    private List<ReportBrokenTicket> reportBrokenTickets;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, targetEntity = LiquidationTicket.class)
    @ToString.Exclude
    private List<LiquidationTicket> liquidationTickets;

    @OneToMany(orphanRemoval = true, mappedBy = "equipment")
    @ToString.Exclude
    private List<Inventory> inventories;

    @Column(nullable = false)
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private EquipmentUnit unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Equipment equipment = (Equipment) o;
        return id != null && Objects.equals(id, equipment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}

