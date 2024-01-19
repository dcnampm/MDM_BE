package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.mapper.EquipmentGroupMapper;
import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentGroupForm;
import com.mdm.equipmentservice.model.dto.fullinfo.EquipmentGroupFullInfoDto;
import com.mdm.equipmentservice.model.entity.EquipmentGroup;
import com.mdm.equipmentservice.model.repository.EquipmentGroupRepository;
import com.mdm.equipmentservice.query.param.GetEquipmentGroupsQueryParam;
import com.mdm.equipmentservice.query.predicate.EquipmentGroupPredicate;
import com.mdm.equipmentservice.service.EquipmentGroupService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.UniqueValidationUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EquipmentGroupServiceImpl implements EquipmentGroupService {
    private final MessageUtil messageUtil;

    private final EquipmentGroupMapper equipmentGroupMapper;

    private final ValidationUtil validationUtil;

    private final UniqueValidationUtil uniqueValidationUtil;

    private final EquipmentGroupRepository equipmentGroupRepository;

    public EquipmentGroupServiceImpl(
            MessageUtil messageUtil, EquipmentGroupMapper equipmentGroupMapper, ValidationUtil validationUtil, UniqueValidationUtil uniqueValidationUtil,
            EquipmentGroupRepository equipmentGroupRepository
    ) {
        this.messageUtil = messageUtil;
        this.equipmentGroupMapper = equipmentGroupMapper;
        this.validationUtil = validationUtil;
        this.uniqueValidationUtil = uniqueValidationUtil;
        this.equipmentGroupRepository = equipmentGroupRepository;
    }

    @Override
    public Page<EquipmentGroupFullInfoDto> getEquipmentGroups(GetEquipmentGroupsQueryParam getEquipmentGroupsQueryParam, Pageable pageable) {
       Predicate equipmentGroupPredicate = EquipmentGroupPredicate.getEquipmentGroupPredicate(getEquipmentGroupsQueryParam);
        return equipmentGroupRepository.findAll(equipmentGroupPredicate, pageable).map(equipmentGroupMapper::toFullInfoDto);
    }

    @Override
    public EquipmentGroupFullInfoDto createEquipmentGroup(UpsertEquipmentGroupForm upsertEquipmentGroupForm) {
        EquipmentGroup equipmentGroup = equipmentGroupMapper.toEntity(upsertEquipmentGroupForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                equipmentGroup,
                equipmentGroupRepository,
                messageUtil.getMessage("equipmentGroupUniquenessValidationFailed")
        );
        equipmentGroup = equipmentGroupRepository.save(equipmentGroup);
        return equipmentGroupMapper.toFullInfoDto(equipmentGroup);
    }

    @Override
    public EquipmentGroupFullInfoDto updateEquipmentGroup(Long equipmentGroupId, UpsertEquipmentGroupForm upsertEquipmentGroupForm) {
        validationUtil.validateNotFound(equipmentGroupId, equipmentGroupRepository, messageUtil.getMessage("equipmentGroupNotFound"));
        EquipmentGroup equipmentGroup = equipmentGroupRepository.findById(equipmentGroupId).orElseThrow();
        equipmentGroup = equipmentGroupMapper.partialUpdate(upsertEquipmentGroupForm, equipmentGroup);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                equipmentGroup,
                equipmentGroupRepository,
                messageUtil.getMessage("equipmentGroupUniquenessValidationFailed")
        );
        equipmentGroup = equipmentGroupRepository.save(equipmentGroup);
        return equipmentGroupMapper.toFullInfoDto(equipmentGroup);
    }

    @Override
    public void deleteEquipmentGroup(Long equipmentGroupId) {
        validationUtil.validateNotFound(equipmentGroupId, equipmentGroupRepository, messageUtil.getMessage("equipmentGroupNotFound"));
        equipmentGroupRepository.deleteById(equipmentGroupId);
        
    }

    @Override
    public EquipmentGroupFullInfoDto getEquipmentGroupById(Long equipmentGroupId) {
        validationUtil.validateNotFound(equipmentGroupId, equipmentGroupRepository, messageUtil.getMessage("equipmentGroupNotFound"));
        EquipmentGroup equipmentGroup = equipmentGroupRepository.findById(equipmentGroupId).orElse(null);
        return equipmentGroupMapper.toFullInfoDto(equipmentGroup);
    }
}
