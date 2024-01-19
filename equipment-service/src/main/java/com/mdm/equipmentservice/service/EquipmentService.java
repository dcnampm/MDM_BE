package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.base.EquipmentDto;
import com.mdm.equipmentservice.model.dto.base.EquipmentSupplyUsageDto;
import com.mdm.equipmentservice.model.dto.form.AttachSupplyForm;
import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentForm;
import com.mdm.equipmentservice.model.dto.fullinfo.EquipmentFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListDto;
import com.mdm.equipmentservice.model.entity.Equipment;
import com.mdm.equipmentservice.query.param.GetEquipmentsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface EquipmentService {
    @PreAuthorize("hasAuthority(\"EQUIPMENT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<EquipmentListDto> getEquipments(GetEquipmentsQueryParam getEquipmentsQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"EQUIPMENT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    List<Equipment> getEquipments(GetEquipmentsQueryParam getEquipmentsQueryParam);

    @PreAuthorize("hasAuthority(\"EQUIPMENT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void generateQr();


    @PreAuthorize("hasAuthority(\"EQUIPMENT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentFullInfoDto getEquipmentById(Long equipmentId) throws AccessDeniedException;

    @PreAuthorize("hasAuthority(\"EQUIPMENT.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentDto createEquipment(UpsertEquipmentForm upsertEquipmentForm, MultipartFile image) throws IOException;

    @PreAuthorize("hasAuthority(\"EQUIPMENT.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentDto updateEquipment(Long equipmentId, UpsertEquipmentForm upsertEquipmentForm, MultipartFile image);

    @PreAuthorize("hasAuthority(\"EQUIPMENT.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteEquipment(Long equipmentId);

    @PreAuthorize("hasAuthority(\"EQUIPMENT.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void createMultipleEquipment(List<UpsertEquipmentForm> upsertEquipmentForms);


    @PreAuthorize("hasAuthority(\"EQUIPMENT.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteMultipleEquipment(List<Long> equipmentIds);

    @PreAuthorize("hasAuthority(\"EQUIPMENT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<EquipmentFullInfoDto> statisticEquipments(GetEquipmentsQueryParam getEquipmentsQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"EQUIPMENT.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentSupplyUsageDto attachSupplies(AttachSupplyForm attachSupplyForm);


}
