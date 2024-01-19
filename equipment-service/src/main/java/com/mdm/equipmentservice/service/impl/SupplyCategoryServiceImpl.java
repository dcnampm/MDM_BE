package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.SupplyCategoryMapper;
import com.mdm.equipmentservice.model.dto.base.SupplyCategoryDto;
import com.mdm.equipmentservice.model.dto.form.UpsertSupplyCategoryForm;
import com.mdm.equipmentservice.model.entity.SupplyCategory;
import com.mdm.equipmentservice.model.repository.SupplyCategoryRepository;
import com.mdm.equipmentservice.query.param.GetSupplyCategoriesQueryParam;
import com.mdm.equipmentservice.query.predicate.QuerySupplyCategoryPredicate;
import com.mdm.equipmentservice.service.SupplyCategoryService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.UniqueValidationUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplyCategoryServiceImpl implements SupplyCategoryService {

     private final SupplyCategoryRepository supplyCategoryRepository;

    private final SupplyCategoryMapper supplyCategoryMapper;

    private final MessageUtil messageUtil;

    private final ValidationUtil validationUtil;

    private final UniqueValidationUtil uniqueValidationUtil;

    @Autowired
    public SupplyCategoryServiceImpl(
            SupplyCategoryRepository supplyCategoryRepository, SupplyCategoryMapper supplyCategoryMapper, MessageUtil messageUtil, ValidationUtil validationUtil,
            UniqueValidationUtil uniqueValidationUtil
    ) {
        this.supplyCategoryRepository = supplyCategoryRepository;
        this.supplyCategoryMapper = supplyCategoryMapper;
        this.messageUtil = messageUtil;
        this.validationUtil = validationUtil;
        this.uniqueValidationUtil = uniqueValidationUtil;
    }

    @Override
    public SupplyCategoryDto getSupplyCategoryById(Long supplyCategoryId) {
        validationUtil.validateNotFound(supplyCategoryId, supplyCategoryRepository, messageUtil.getMessage("supplyCategoryNotFound"));
        SupplyCategory supplyCategory = supplyCategoryRepository.findById(supplyCategoryId).orElse(null);
        return supplyCategoryMapper.toDto(supplyCategory);
    }

    @Override
    public Page<SupplyCategoryDto> getSupplyCategories(GetSupplyCategoriesQueryParam getSupplyCategoriesQueryParam, Pageable pageable) {
        return supplyCategoryRepository.findAll(QuerySupplyCategoryPredicate.getAllSupplyCategoriesPredicate(getSupplyCategoriesQueryParam), pageable)
                .map(supplyCategoryMapper::toDto);
    }

    @Override
    public SupplyCategoryDto createSupplyCategory(UpsertSupplyCategoryForm upsertSupplyCategoryForm) {
        SupplyCategory supplyCategoryToBeCreated = supplyCategoryMapper.toEntity(upsertSupplyCategoryForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                supplyCategoryToBeCreated,
                supplyCategoryRepository,
                messageUtil.getMessage("supplyCategoryUniquenessValidationFailed")
        );
        supplyCategoryToBeCreated = supplyCategoryRepository.save(supplyCategoryToBeCreated);
        return supplyCategoryMapper.toDto(supplyCategoryToBeCreated);
    }

    @Override
    public SupplyCategoryDto updateSupplyCategory(Long supplyCategoryId, UpsertSupplyCategoryForm upsertSupplyCategoryForm) {
        SupplyCategory supplyCategoryToBeUpdated = supplyCategoryRepository.findById(supplyCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("supplyCategoryNotFound")));
        supplyCategoryToBeUpdated = supplyCategoryMapper.partialUpdate(upsertSupplyCategoryForm, supplyCategoryToBeUpdated);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                supplyCategoryToBeUpdated,
                supplyCategoryRepository,
                messageUtil.getMessage("supplyCategoryUniquenessValidationFailed")
        );
        supplyCategoryToBeUpdated = supplyCategoryRepository.save(supplyCategoryToBeUpdated);
        return supplyCategoryMapper.toDto(supplyCategoryToBeUpdated);
    }

    @Override
    @Transactional
    public void deleteSupplyCategory(Long supplyCategoryId) {
        validationUtil.validateNotFound(supplyCategoryId, supplyCategoryRepository, messageUtil.getMessage("supplyCategoryNotFound"));
        supplyCategoryRepository.deleteById(supplyCategoryId);
    }
}
