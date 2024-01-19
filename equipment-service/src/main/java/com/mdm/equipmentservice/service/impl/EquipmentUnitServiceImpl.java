package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.EquipmentUnitMapper;
import com.mdm.equipmentservice.model.dto.base.EquipmentUnitDto;
import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentUnitForm;
import com.mdm.equipmentservice.model.entity.EquipmentUnit;
import com.mdm.equipmentservice.model.repository.EquipmentUnitRepository;
import com.mdm.equipmentservice.query.param.GetEquipmentUnitsQueryParam;
import com.mdm.equipmentservice.query.predicate.QueryEquipmentUnitPredicate;
import com.mdm.equipmentservice.service.EquipmentUnitService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.UniqueValidationUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class EquipmentUnitServiceImpl implements EquipmentUnitService {

    private final EquipmentUnitRepository equipmentUnitRepository;

    private final EquipmentUnitMapper equipmentUnitMapper;

    private final MessageUtil messageUtil;

    private final ValidationUtil validationUtil;

    private final UniqueValidationUtil uniqueValidationUtil;

    @Autowired
    public EquipmentUnitServiceImpl(EquipmentUnitRepository equipmentUnitRepository, EquipmentUnitMapper equipmentUnitMapper, MessageUtil messageUtil,
                                    ValidationUtil validationUtil, UniqueValidationUtil uniqueValidationUtil) {
        this.equipmentUnitRepository = equipmentUnitRepository;
        this.equipmentUnitMapper = equipmentUnitMapper;
        this.messageUtil = messageUtil;
        this.validationUtil = validationUtil;
        this.uniqueValidationUtil = uniqueValidationUtil;
    }

    @Override
    public EquipmentUnitDto getEquipmentUnitById(Long equipmentUnitId) {
        validationUtil.validateNotFound(equipmentUnitId, equipmentUnitRepository, messageUtil.getMessage("equipmentUnitNotFound"));
        EquipmentUnit equipmentUnit = equipmentUnitRepository.findById(equipmentUnitId).orElse(null);
        return equipmentUnitMapper.toDto(equipmentUnit);
    }

    @Override
    public Page<EquipmentUnitDto> getEquipmentUnits(GetEquipmentUnitsQueryParam getEquipmentUnitsQueryParam, Pageable pageable) {
        return equipmentUnitRepository.findAll(QueryEquipmentUnitPredicate.getEquipmentUnitsPredicate(getEquipmentUnitsQueryParam), pageable)
                .map(equipmentUnitMapper::toDto);
    }

    @Override
    public EquipmentUnitDto createEquipmentUnit(UpsertEquipmentUnitForm upsertEquipmentUnitForm) {
        EquipmentUnit equipmentUnitToBeCreated = equipmentUnitMapper.toEntity(upsertEquipmentUnitForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                equipmentUnitToBeCreated,
                equipmentUnitRepository,
                messageUtil.getMessage("equipmentUnitUniquenessValidationFailed")
        );
        equipmentUnitToBeCreated = equipmentUnitRepository.save(equipmentUnitToBeCreated);
        return equipmentUnitMapper.toDto(equipmentUnitToBeCreated);
    }

    @Override
    public EquipmentUnitDto updateEquipmentUnit(Long equipmentUnitId, UpsertEquipmentUnitForm upsertEquipmentUnitForm) {
        EquipmentUnit equipmentUnitToBeUpdated = equipmentUnitRepository.findById(equipmentUnitId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentUnitNotFound")));
        equipmentUnitToBeUpdated = equipmentUnitMapper.partialUpdate(equipmentUnitToBeUpdated, upsertEquipmentUnitForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                equipmentUnitToBeUpdated,
                equipmentUnitRepository,
                messageUtil.getMessage("equipmentUnitUniquenessValidationFailed")
        );
        equipmentUnitToBeUpdated = equipmentUnitRepository.save(equipmentUnitToBeUpdated);
        return equipmentUnitMapper.toDto(equipmentUnitToBeUpdated);
    }

    @Override
    @Transactional
    public void deleteEquipmentUnit(Long equipmentUnitId) {
        validationUtil.validateNotFound(equipmentUnitId, equipmentUnitRepository, messageUtil.getMessage("equipmentUnitNotFound"));
        equipmentUnitRepository.deleteById(equipmentUnitId);
    }

}
