package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.DepartmentMapper;
import com.mdm.equipmentservice.model.dto.form.UpsertDepartmentForm;
import com.mdm.equipmentservice.model.dto.fullinfo.DepartmentFullInfoDto;
import com.mdm.equipmentservice.model.entity.Department;
import com.mdm.equipmentservice.model.repository.DepartmentRepository;
import com.mdm.equipmentservice.query.param.GetDepartmentsQueryParam;
import com.mdm.equipmentservice.query.predicate.QueryDepartmentPredicate;
import com.mdm.equipmentservice.service.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    private final MessageUtil messageUtil;

    private final ValidationUtil validationUtil;

    private final UniqueValidationUtil uniqueValidationUtil;

    @Autowired
    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository, DepartmentMapper departmentMapper, MessageUtil messageUtil, ValidationUtil validationUtil,
            UniqueValidationUtil uniqueValidationUtil
    ) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.messageUtil = messageUtil;
        this.validationUtil = validationUtil;
        this.uniqueValidationUtil = uniqueValidationUtil;
    }

    @Override
    public DepartmentFullInfoDto getDepartmentById(Long departmentId) {
        validationUtil.validateNotFound(departmentId, departmentRepository, messageUtil.getMessage("departmentNotFound"));
        Department department = departmentRepository.findById(departmentId).orElse(null);
        return departmentMapper.toFullInfoDto(department);
    }

    @Override
    public Page<DepartmentFullInfoDto> getDepartments(GetDepartmentsQueryParam getDepartmentsQueryParam, Pageable pageable) {
        return departmentRepository.findAll(QueryDepartmentPredicate.getAllDepartmentsPredicate(getDepartmentsQueryParam), pageable)
                .map(departmentMapper::toFullInfoDto);
    }

    @Override
    public DepartmentFullInfoDto createDepartment(UpsertDepartmentForm upsertDepartmentForm) {
        Department departmentToBeCreated = departmentMapper.toEntity(upsertDepartmentForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                departmentToBeCreated,
                departmentRepository,
                messageUtil.getMessage("departmentUniquenessValidationFailed")
        );
        departmentToBeCreated = departmentRepository.save(departmentToBeCreated);
        return departmentMapper.toFullInfoDto(departmentToBeCreated);
    }

    @Override
    public DepartmentFullInfoDto updateDepartment(Long departmentId, UpsertDepartmentForm upsertDepartmentForm) {
        Department departmentToBeUpdated = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("departmentNotFound")));
        departmentToBeUpdated = departmentMapper.partialUpdate(upsertDepartmentForm, departmentToBeUpdated);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                departmentToBeUpdated,
                departmentRepository,
                messageUtil.getMessage("departmentUniquenessValidationFailed")
        );
        departmentToBeUpdated = departmentRepository.save(departmentToBeUpdated);
        return departmentMapper.toFullInfoDto(departmentToBeUpdated);
    }

    @Override
    @Transactional
    public void deleteDepartment(Long departmentId) {
        validationUtil.validateNotFound(departmentId, departmentRepository, messageUtil.getMessage("departmentNotFound"));
        departmentRepository.deleteById(departmentId);
    }
}
