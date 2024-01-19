package com.mdm.equipmentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(
        name = "supplies", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"serial"}, name = "serial")
}
)
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String model;

    @Column(unique = true)
    private String serial;

    private String hashCode;

    @Column(nullable = false)
    private Double amountUsed;

    @Column(nullable = false)
    private Double amount;

    private Date warehouseImportDate;

    private Integer yearOfManufacture;

    private Integer yearInUse;

    private Double importPrice;

    @OneToMany(mappedBy = "supply", targetEntity = EquipmentSupplyUsage.class)
    private List<EquipmentSupplyUsage> equipmentSupplyUsages;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private SupplyUnit unit;

    @ManyToOne
    @JoinColumn(name = "supply_category_id")
    private SupplyCategory category;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    private String manufacturer;

    private String manufacturingCountry;

    @ManyToOne(targetEntity = Supplier.class)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    private Date expiryDate;

    @Column(columnDefinition = "TEXT")
    private String technicalParameter;

    @Column(columnDefinition = "TEXT")
    private String configuration;

    @Column(columnDefinition = "TEXT")
    private String usageProcedure;

    @Column(columnDefinition = "TEXT")
    private String note;

}
