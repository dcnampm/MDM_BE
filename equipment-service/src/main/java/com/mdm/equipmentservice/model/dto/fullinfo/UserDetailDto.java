package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.dto.base.DepartmentDto;
import com.mdm.equipmentservice.model.entity.WorkingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto implements Serializable {
    private Long id;

    private String name;

    private String username;

    private String email;

    private String phone;

    private Boolean gender;

    private String address;

    private LocalDateTime birthday;

    private boolean enabled;

    private WorkingStatus workingStatus;

    private RoleFullInfoDto role;

    private DepartmentDto department;

    private List<DepartmentDto> departmentResponsibilities;
    private List<SimpleGrantedAuthority> grantedAuthorities;
    private Long imageId;
}