package com.mdm.equipmentservice.service.impl;


import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.SupplierMapper;
import com.mdm.equipmentservice.model.dto.form.UpsertSupplierForm;
import com.mdm.equipmentservice.model.dto.fullinfo.SupplierFullInfoDto;
import com.mdm.equipmentservice.model.entity.Supplier;
import com.mdm.equipmentservice.model.repository.SupplierRepository;
import com.mdm.equipmentservice.query.param.GetSuppliersQueryParam;
import com.mdm.equipmentservice.query.predicate.QuerySupplierPredicate;
import com.mdm.equipmentservice.service.SupplierService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.UniqueValidationUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Import({ValidationUtil.class, UniqueValidationUtil.class})
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;

    private final MessageUtil messageUtil;

    private final ValidationUtil validationUtil;

    private final UniqueValidationUtil uniqueValidationUtil;

    @Autowired
    public SupplierServiceImpl(
            SupplierRepository supplierRepository, SupplierMapper supplierMapper, MessageUtil messageUtil, ValidationUtil validationUtil,
            UniqueValidationUtil uniqueValidationUtil
    ) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
        this.messageUtil = messageUtil;
        this.validationUtil = validationUtil;
        this.uniqueValidationUtil = uniqueValidationUtil;
    }

    @Override
    public SupplierFullInfoDto getSupplierById(Long supplierId) {
        validationUtil.validateNotFound(supplierId, supplierRepository, messageUtil.getMessage("supplierNotFound"));
        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
        return supplierMapper.toDto(supplier);
    }

    @Override
    public Page<SupplierFullInfoDto> getSuppliers(GetSuppliersQueryParam getSuppliersQueryParam, Pageable pageable) {
        return supplierRepository.findAll(QuerySupplierPredicate.getAllSuppliersPredicate(getSuppliersQueryParam), pageable)
                .map(supplierMapper::toDto);
    }

    @Override
    public SupplierFullInfoDto createSupplier(UpsertSupplierForm upsertSupplierForm) {
        Supplier supplierToBeCreated = supplierMapper.toEntity(upsertSupplierForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                supplierToBeCreated,
                supplierRepository,
                messageUtil.getMessage("supplierUniquenessValidationFailed")
        );
        supplierToBeCreated = supplierRepository.saveAndFlush(supplierToBeCreated);
        return supplierMapper.toDto(supplierToBeCreated);
    }

    @Override
    public SupplierFullInfoDto updateSupplier(Long supplierId, UpsertSupplierForm upsertSupplierForm) {
        Supplier supplierToBeUpdated = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("supplierNotFound")));
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                supplierToBeUpdated,
                supplierRepository,
                messageUtil.getMessage("supplierUniquenessValidationFailed")
        );
        supplierToBeUpdated = supplierMapper.partialUpdate(upsertSupplierForm, supplierToBeUpdated);
        supplierToBeUpdated = supplierRepository.saveAndFlush(supplierToBeUpdated);
        return supplierMapper.toDto(supplierToBeUpdated);
    }

    @Override
    @Transactional
    public void deleteSupplier(Long supplierId) {
        validationUtil.validateNotFound(supplierId, supplierRepository, messageUtil.getMessage("supplierNotFound"));
        supplierRepository.deleteById(supplierId);
    }

    @Override
    public void deleteMultipleSupplier(List<Long> supplierIds) {
        if (supplierIds == null || supplierIds.isEmpty()) {
            return;
        }
        supplierRepository.deleteAllByIdInBatch(supplierIds);
    }
}
