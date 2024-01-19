package com.mdm.equipmentservice.controller;


import com.mdm.equipmentservice.model.dto.base.ServiceDto;
import com.mdm.equipmentservice.model.dto.form.UpsertServiceForm;
import com.mdm.equipmentservice.query.param.GetServicesQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.ServiceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/services")
public class ServiceController {
    private final ServiceService serviceService;

    private final PagedResourcesAssembler<ServiceDto> serviceDtoPagedResourcesAssembler;

    public ServiceController(
            ServiceService serviceService,
            PagedResourcesAssembler<ServiceDto> serviceDtoPagedResourcesAssembler
    ) {
        this.serviceService = serviceService;
        this.serviceDtoPagedResourcesAssembler = serviceDtoPagedResourcesAssembler;
    }


    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<ServiceDto>>>> getServices(
            GetServicesQueryParam getServicesQueryParam, Pageable pageable
    ) {
        PagedModel<EntityModel<ServiceDto>> entityModels = serviceDtoPagedResourcesAssembler.toModel(
                serviceService.getServices(
                        getServicesQueryParam,
                        pageable
                ));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ServiceDto>> getService(@PathVariable(name = "id") Long serviceId) {
        ServiceDto serviceDto = serviceService.getServiceById(serviceId);
        return ResponseEntity.ok(new GenericResponse<>(serviceDto));
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<ServiceDto>> createService(
            @RequestBody @Valid UpsertServiceForm upsertServiceForm
    ) {
        ServiceDto serviceDto = serviceService.createService(upsertServiceForm);
        return new ResponseEntity<>(new GenericResponse<>(serviceDto, 201), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<ServiceDto>> updateService(
            @PathVariable(name = "id") Long serviceId, @RequestBody @Valid UpsertServiceForm upsertServiceForm
    ) {
        ServiceDto serviceDto = serviceService.updateService(
                serviceId,
                upsertServiceForm
        );
        return ResponseEntity.ok(new GenericResponse<>(serviceDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteMapping(@PathVariable(name = "id") Long serviceId) {
        serviceService.deleteService(serviceId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }
}
