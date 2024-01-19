package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.mapper.EquipmentCategoryMapper;
import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentCategoryForm;
import com.mdm.equipmentservice.model.dto.fullinfo.EquipmentCategoryFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentCategoryListDto;
import com.mdm.equipmentservice.model.entity.EquipmentCategory;
import com.mdm.equipmentservice.model.repository.EquipmentCategoryRepository;
import com.mdm.equipmentservice.query.param.GetEquipmentCategoriesQueryParam;
import com.mdm.equipmentservice.query.predicate.EquipmentCategoryPredicate;
import com.mdm.equipmentservice.service.EquipmentCategoryService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.UniqueValidationUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Import({ValidationUtil.class, UniqueValidationUtil.class})
public class EquipmentCategoryServiceImpl implements EquipmentCategoryService {
    private final MessageUtil messageUtil;

    private final EquipmentCategoryMapper equipmentCategoryMapper;

    private final ValidationUtil validationUtil;

    private final UniqueValidationUtil uniqueValidationUtil;

    private final EquipmentCategoryRepository equipmentCategoryRepository;

    public EquipmentCategoryServiceImpl(
            MessageUtil messageUtil, EquipmentCategoryMapper equipmentCategoryMapper, ValidationUtil validationUtil, UniqueValidationUtil uniqueValidationUtil,
            EquipmentCategoryRepository equipmentCategoryRepository
    ) {
        this.messageUtil = messageUtil;
        this.equipmentCategoryMapper = equipmentCategoryMapper;
        this.validationUtil = validationUtil;
        this.uniqueValidationUtil = uniqueValidationUtil;
        this.equipmentCategoryRepository = equipmentCategoryRepository;
    }

    @Override
    public Page<EquipmentCategoryListDto> getEquipmentCategories(
            GetEquipmentCategoriesQueryParam getEquipmentCategoriesQueryParam, Pageable pageable
    ) {
        Predicate equipmentCategoryPredicate = EquipmentCategoryPredicate.getEquipmentCategoryPredicate(getEquipmentCategoriesQueryParam);
        return equipmentCategoryRepository.findAll(equipmentCategoryPredicate, pageable).map(equipmentCategoryMapper::toListDto);
    }

    @Override
    public EquipmentCategoryFullInfoDto getEquipmentCategoryById(Long equipmentCategoryId) {
        validationUtil.validateNotFound(equipmentCategoryId, equipmentCategoryRepository, messageUtil.getMessage("equipmentCategoryNotFound"));
        EquipmentCategory equipmentCategory = equipmentCategoryRepository.findById(equipmentCategoryId).orElse(null);
        return equipmentCategoryMapper.toFullInfoDto(equipmentCategory);
    }

    @Override
    public EquipmentCategoryFullInfoDto createEquipmentCategory(UpsertEquipmentCategoryForm upsertEquipmentCategoryForm) {
        EquipmentCategory equipmentCategory = equipmentCategoryMapper.toEntity(upsertEquipmentCategoryForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                equipmentCategory,
                equipmentCategoryRepository,
                messageUtil.getMessage("equipmentCategoryUniquenessValidationFailed")
        );
        equipmentCategory = equipmentCategoryRepository.save(equipmentCategory);
        return equipmentCategoryMapper.toFullInfoDto(equipmentCategory);
    }

    @Override
    public EquipmentCategoryFullInfoDto updateEquipmentCategory(Long equipmentCategoryId, UpsertEquipmentCategoryForm upsertEquipmentCategoryForm) {
        validationUtil.validateNotFound(equipmentCategoryId, equipmentCategoryRepository, messageUtil.getMessage("equipmentCategoryNotFound"));
        EquipmentCategory equipmentCategory = equipmentCategoryRepository.findById(equipmentCategoryId).orElseThrow();
        equipmentCategory = equipmentCategoryMapper.partialUpdate(upsertEquipmentCategoryForm, equipmentCategory);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                equipmentCategory,
                equipmentCategoryRepository,
                messageUtil.getMessage("equipmentCategoryUniquenessValidationFailed")
        );
        equipmentCategory = equipmentCategoryRepository.save(equipmentCategory);
        return equipmentCategoryMapper.toFullInfoDto(equipmentCategory);
    }

    @Override
    public void deleteEquipmentCategory(Long equipmentCategoryId) {
        validationUtil.validateNotFound(equipmentCategoryId, equipmentCategoryRepository, messageUtil.getMessage("equipmentCategoryNotFound"));
        equipmentCategoryRepository.deleteById(equipmentCategoryId);
    }


}
