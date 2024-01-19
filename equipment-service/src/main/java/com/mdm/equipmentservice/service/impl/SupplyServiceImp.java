package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.exception.ValidationFailedException;
import com.mdm.equipmentservice.mapper.SupplyMapper;
import com.mdm.equipmentservice.model.dto.form.UpsertSupplyForm;
import com.mdm.equipmentservice.model.dto.fullinfo.SupplyFullInfoDto;
import com.mdm.equipmentservice.model.entity.Supply;
import com.mdm.equipmentservice.model.repository.SupplyRepository;
import com.mdm.equipmentservice.query.param.GetSupplyQueryParam;
import com.mdm.equipmentservice.service.SupplyService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.UniqueValidationUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import com.querydsl.core.types.Predicate;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mdm.equipmentservice.query.predicate.QuerySupplyPredicate.getAllPredicate;

@Service
@Import({ValidationUtil.class, UniqueValidationUtil.class})
public class SupplyServiceImp implements SupplyService {

    private final SupplyRepository supplyRepository;

    private final SupplyMapper supplyMapper;

    private final UniqueValidationUtil uniqueValidationUtil;

    private final ValidationUtil validationUtil;

    private final MessageUtil messageUtil;

    public SupplyServiceImp(
            SupplyRepository supplyRepository, SupplyMapper supplyMapper, UniqueValidationUtil uniqueValidationUtil, ValidationUtil validationUtil,
            MessageUtil messageUtil
    ) {
        this.supplyRepository = supplyRepository;
        this.supplyMapper = supplyMapper;
        this.uniqueValidationUtil = uniqueValidationUtil;
        this.validationUtil = validationUtil;
        this.messageUtil = messageUtil;
    }

    @Override
    public Page<SupplyFullInfoDto> getAllSupplies(GetSupplyQueryParam getSupplyQueryParam, Pageable pageable) {
        Predicate predicate = getAllPredicate(getSupplyQueryParam);
        return supplyRepository.findAll(predicate, pageable).map(supplyMapper::toFullInfoDto);
    }

    @Override
    public SupplyFullInfoDto getSupply(Long id) {
        validationUtil.validateNotFound(id, supplyRepository, messageUtil.getMessage("supplyNotFound"));
        Supply supply = supplyRepository.findById(id).orElseThrow();
        return supplyMapper.toFullInfoDto(supply);
    }

    @Override
    public SupplyFullInfoDto create(UpsertSupplyForm upsertSupplyForm) {
        Supply supply = supplyMapper.toSupplyEntity(upsertSupplyForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                supply,
                supplyRepository,
                messageUtil.getMessage("supplyUniquenessValidationFailed")
        );
        supply = supplyRepository.save(supply);
        return supplyMapper.toFullInfoDto(supply);

    }

    @Override
    public SupplyFullInfoDto update(UpsertSupplyForm upsertSupplyForm, Long id) {

        validationUtil.validateNotFound(id, supplyRepository, messageUtil.getMessage("supplyNotFound"));
        Supply supplyToBeUpdated = supplyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("supplyNotFound")));
        supplyToBeUpdated = supplyMapper.partialUpdate(upsertSupplyForm, supplyToBeUpdated);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                supplyToBeUpdated,
                supplyRepository,
                messageUtil.getMessage("supplyUniquenessValidationFailed")
        );
        supplyToBeUpdated = supplyRepository.save(supplyToBeUpdated);
        return supplyMapper.toFullInfoDto(supplyToBeUpdated);
    }

    @Override
    public void deleteSupply(Long supplyId) {
        validationUtil.validateNotFound(supplyId, supplyRepository, messageUtil.getMessage("equipmentCategoryNotFound"));
        supplyRepository.deleteById(supplyId);
    }

    @Override
    @Transactional
    public void createMultipleSupply(List<UpsertSupplyForm> upsertSupplyForms) {
        validationUtil.validateConstraintsOnListOfDto_andThrowExceptionIfHasError(upsertSupplyForms, messageUtil.getMessage("supplyValidationFailed"));
        List<Supply> supplies = upsertSupplyForms.parallelStream().map(supplyMapper::toSupplyEntity).toList();
        //TODO: Optimize the process to check the uniqueness
        List<String> uniquenessValidationErrorMessages = uniqueValidationUtil.validateUniqueOnListOfEntity(supplies, supplyRepository);
        if (!uniquenessValidationErrorMessages.isEmpty()) {
            throw new ValidationFailedException(messageUtil.getMessage("supplyListUniquenessValidationFailed"), uniquenessValidationErrorMessages);
        }
        supplyRepository.saveAll(supplies);
    }

    @Override
    public void deleteMultipleSupply(List<Long> supplyIds) {
        if (supplyIds.isEmpty())
            return;
        supplyRepository.deleteAllById(supplyIds);
    }
}
