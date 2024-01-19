package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.Supplier;
import jakarta.validation.constraints.NotBlank;
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
public class UpsertSupplierForm implements Serializable {
    @NotBlank(message = "{supplierNameMustNotBeBlank}")
    private String name;

    private String address;

    @NotBlank(message = "{supplierHotlineMustNotBeBlank}")
    private String hotline;

    @NotBlank(message = "{supplierEmailMustNotBeBlank}")
    private String email;

    private String fax;

    private String website;

    private String taxCode;

    private Long contactPersonId;

    private String note;

    private List<Long> serviceIds;
}