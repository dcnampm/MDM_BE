package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.mapper.ServiceMapper;
import com.mdm.equipmentservice.model.dto.base.ServiceDto;
import com.mdm.equipmentservice.model.dto.form.UpsertServiceForm;
import com.mdm.equipmentservice.model.entity.Service;
import com.mdm.equipmentservice.model.repository.ServiceRepository;
import com.mdm.equipmentservice.query.param.GetServicesQueryParam;
import com.mdm.equipmentservice.query.predicate.ServicePredicate;
import com.mdm.equipmentservice.service.ServiceService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.UniqueValidationUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@org.springframework.stereotype.Service
@Slf4j
public class ServiceServiceImpl implements ServiceService {

    private final MessageUtil messageUtil;

    private final ServiceMapper serviceMapper;

    private final ValidationUtil validationUtil;

    private final UniqueValidationUtil uniqueValidationUtil;

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(
            MessageUtil messageUtil, ServiceMapper serviceMapper, ValidationUtil validationUtil, UniqueValidationUtil uniqueValidationUtil,
            ServiceRepository serviceRepository
    ) {
        this.messageUtil = messageUtil;
        this.serviceMapper = serviceMapper;
        this.validationUtil = validationUtil;
        this.uniqueValidationUtil = uniqueValidationUtil;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Page<ServiceDto> getServices(GetServicesQueryParam getServicesQueryParam, Pageable pageable) {
        Predicate servicePredicate = ServicePredicate.getServicePredicate(getServicesQueryParam);
        return serviceRepository.findAll(servicePredicate, pageable).map(serviceMapper::toDto);
    }

    @Override
    public ServiceDto createService(UpsertServiceForm upsertServiceForm) {
        Service service = serviceMapper.toEntity(upsertServiceForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                service,
                serviceRepository,
                messageUtil.getMessage("serviceUniquenessValidationFailed")
        );
        service = serviceRepository.save(service);
        return serviceMapper.toDto(service);
    }

    @Override
    public ServiceDto updateService(Long serviceId, UpsertServiceForm upsertServiceForm) {
        validationUtil.validateNotFound(serviceId, serviceRepository, messageUtil.getMessage("serviceNotFound"));
        Service service = serviceRepository.findById(serviceId).orElseThrow();
        service = serviceMapper.partialUpdate(upsertServiceForm, service);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                service,
                serviceRepository,
                messageUtil.getMessage("serviceUniquenessValidationFailed")
        );
        service = serviceRepository.save(service);
        return serviceMapper.toDto(service);
    }

    @Override
    public void deleteService(Long serviceId) {
        validationUtil.validateNotFound(serviceId, serviceRepository, messageUtil.getMessage("serviceNotFound"));
        serviceRepository.deleteById(serviceId);

    }

    @Override
    public ServiceDto getServiceById(Long serviceId) {
        validationUtil.validateNotFound(serviceId, serviceRepository, messageUtil.getMessage("serviceNotFound"));
        Service service = serviceRepository.findById(serviceId).orElse(null);
        return serviceMapper.toDto(service);
    }
}
