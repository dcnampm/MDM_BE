package com.mdm.equipmentservice.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "suppliers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}, name = "email"),
        @UniqueConstraint(columnNames = {"taxCode"}, name = "taxCode"),
        @UniqueConstraint(columnNames = {"hotline"}, name = "hotline")
})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(unique = true, nullable = false)
    private String hotline;

    @Column(unique = true, nullable = false)
    private String email;

    private String fax;

    private String website;

    private String taxCode;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "contact_person_id", referencedColumnName = "id")
    private User contactPerson;

    private String note;

    @OneToMany(mappedBy = "supplier", targetEntity = Equipment.class)
    private List<Equipment> equipments;

    @OneToMany(mappedBy = "supplier", targetEntity = Supply.class)
    private List<Supply> supplies;

    @ManyToMany(targetEntity = Service.class)
    @JoinTable(name = "provider_provides_services",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Service> services;

    
}
