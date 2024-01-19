package com.mdm.equipmentservice.model.dto.base;

import com.mdm.equipmentservice.model.entity.User;
import com.mdm.equipmentservice.model.entity.WorkingStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserDto implements Serializable {
    private final Long id;

    private final String name;

    private final String username;

    private final String email;

    private final String phone;

    private final Boolean gender;

    private final String address;

    private final LocalDateTime birthday;

    private final boolean enabled;

    private final WorkingStatus workingStatus;
}
