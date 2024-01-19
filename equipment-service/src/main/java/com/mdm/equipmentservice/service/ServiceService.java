package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.base.ServiceDto;
import com.mdm.equipmentservice.model.dto.form.UpsertServiceForm;
import com.mdm.equipmentservice.query.param.GetServicesQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ServiceService {
    @PreAuthorize("hasAuthority(\"SERVICE.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<ServiceDto> getServices(GetServicesQueryParam getServicesQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"SERVICE.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    ServiceDto createService(UpsertServiceForm upsertServiceForm);

    @PreAuthorize("hasAuthority(\"SERVICE.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    ServiceDto updateService(Long serviceId, UpsertServiceForm upsertServiceForm);

    @PreAuthorize("hasAuthority(\"SERVICE.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteService(Long serviceId);

    @PreAuthorize("hasAuthority(\"SERVICE.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    ServiceDto getServiceById(Long serviceId);
}
