package com.mdm.equipmentservice.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(
        name = "departments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"phone"}, name = "phone"), @UniqueConstraint(columnNames = {"email"}, name = "email")
}
)
@NamedEntityGraph(
        name = "departmentWithUser", attributeNodes = {
        @NamedAttributeNode("contactPerson"), @NamedAttributeNode("headOfDepartment"), @NamedAttributeNode("manager"), @NamedAttributeNode("chiefNurse")
}
)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Schema(hidden = true)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String alias;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    private String address;

    @Enumerated(EnumType.STRING)
    private DepartmentActiveStatus activeStatus;

    //đầu mối liên hệ
    @OneToOne
    @JoinColumn(name = "contact_person_id", foreignKey = @ForeignKey(name = "contact_person_id"))
    private User contactPerson;

    //trưởng khoa
    @OneToOne
    @JoinColumn(name = "head_of_department_id", foreignKey = @ForeignKey(name = "head_of_department_id"))
    private User headOfDepartment;

    //điều dưỡnng trưởng
    @OneToOne
    @JoinColumn(name = "chief_nurse_id", foreignKey = @ForeignKey(name = "chief_nurse_id"))
    private User chiefNurse;

    //Ban giám đốc quản lý nhiều khoa phòng
    @ManyToOne
    @JoinColumn(name = "manager_id", foreignKey = @ForeignKey(name = "manager"))
    private User manager;

    @OneToMany(mappedBy = "department", targetEntity = User.class, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<User> users;


}
