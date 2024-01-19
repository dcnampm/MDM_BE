package com.mdm.equipmentservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(
        name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}, name = "username"),
        @UniqueConstraint(columnNames = {"phone"}, name = "phone"),
        @UniqueConstraint(columnNames = {"email"}, name = "email")
}
)
@NamedEntityGraph(
        name = "userFullInfo", subgraphs = @NamedSubgraph(name = "role-subgraph", attributeNodes = @NamedAttributeNode("permissions")),
        attributeNodes = {
                @NamedAttributeNode("department"),
                @NamedAttributeNode("departmentResponsibilities"),
                @NamedAttributeNode(value = "role", subgraph = "role-subgraph")
        }
)
@NamedEntityGraph(
        name = "userWithRole", subgraphs = @NamedSubgraph(name = "role-subgraph", attributeNodes = @NamedAttributeNode("permissions")),
        attributeNodes = {
                @NamedAttributeNode("department"),
                @NamedAttributeNode("departmentResponsibilities"),
                @NamedAttributeNode(value = "role", subgraph = "role-subgraph")
        }
)
public class User implements UserDetails {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false) //true is male, false is female
    private Boolean gender;

    private String address;

    @Column(nullable = false)
    private LocalDateTime birthday;

    @Column(nullable = false)
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private WorkingStatus workingStatus;

    @JoinColumn(name = "role_id")
    @ManyToOne(targetEntity = Role.class)
    private Role role;

    @ManyToOne()
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "department"))
    private Department department;

    //phụ trách khoa phòng
    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Department> departmentResponsibilities;

    @Column(nullable = false)
    private String password;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.getName()));
        Set<Permission> permissions = this.role.getPermissions();
        if (permissions == null || permissions.isEmpty()) {
            return authorities;
        }
        permissions.forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        });

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {return  true;}
}