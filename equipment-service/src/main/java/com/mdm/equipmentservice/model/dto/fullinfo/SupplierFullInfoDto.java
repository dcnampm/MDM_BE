package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.dto.base.ServiceDto;
import com.mdm.equipmentservice.model.dto.base.UserDto;
import com.mdm.equipmentservice.model.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Supplier}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierFullInfoDto implements Serializable {
    private Long id;

    private String name;

    private String address;

    private String hotline;

    private String email;

    private String fax;

    private String website;

    private String taxCode;

    private UserDto contactPerson;

    private String note;

    private List<ServiceDto> services;
}