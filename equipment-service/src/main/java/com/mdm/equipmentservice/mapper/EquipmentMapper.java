package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.EquipmentExcelDto;
import com.mdm.equipmentservice.model.dto.EquipmentQrDto;
import com.mdm.equipmentservice.model.dto.base.EquipmentDto;
import com.mdm.equipmentservice.model.dto.form.*;
import com.mdm.equipmentservice.model.dto.fullinfo.EquipmentFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.*;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        IdToEntityMapper.class,
        RepairTicketMapper.class,
        ReportBrokenTicketMapper.class,
        HandoverTicketMapper.class,
        LiquidationTicketMapper.class,
        TransferTicketMapper.class,
        DepartmentMapper.class,
        InspectionTicketMapper.class,
        SupplierMapper.class,
        ProjectMapper.class,
        InventoryMapper.class,
        EquipmentUnitMapper.class,
        MaintenanceTicketMapper.class,
        EquipmentCategoryMapper.class
}, imports = {List.class, CollectionUtils.class, FileDescription.class, EquipmentStatus.class, CommonUtil.class}
)
public abstract class EquipmentMapper {

    @Autowired
    protected FileStorageService fileStorageService;


    @Autowired
    protected IdToEntityMapper idToEntityMapper;

    @Mapping(target = "departmentId", ignore = true)
    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(source = "category.id", target = "categoryId")
    public abstract EquipmentDto toDto(Equipment equipment);

    @Mapping(
            target = "attachments",
            expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(equipment.getClass().getSimpleName(),equipment.getId(),FileDescription.IMAGE))"
    )
    public abstract EquipmentFullInfoDto toFullInfoDto(Equipment equipment);

    @Mapping(target = "unit", source = "unitId")
    @Mapping(target = "department", source = "upsertEquipmentForm.departmentId")
    @Mapping(target = "category", source = "upsertEquipmentForm.categoryId")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract Equipment toEntity(UpsertEquipmentForm upsertEquipmentForm);

    @Mapping(target = "unit", source = "upsertEquipmentForm.unitId")
    @Mapping(target = "department", source = "upsertEquipmentForm.departmentId")
    @Mapping(target = "supplier", source = "upsertEquipmentForm.supplierId")
    @Mapping(target = "project", source = "upsertEquipmentForm.projectId")
    @Mapping(target = "category", source = "upsertEquipmentForm.categoryId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract Equipment toEntity_updateCase(UpsertEquipmentForm upsertEquipmentForm, @MappingTarget Equipment equipment);

    @Mapping(source = "riskLevel", target = "riskLevel")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "unit", target = "unitName", qualifiedByName = "toUnitName")
    @Mapping(source = "department", target = "departmentName", qualifiedByName = "toDepartmentName")
    @Mapping(source = "category", target = "categoryName", qualifiedByName = "toCategoryName")
    @Mapping(source = "category", target = "categoryGroupName", qualifiedByName = "toCategoryGroupName")
    @Mapping(source = "supplier", target = "supplierName", qualifiedByName = "toSupplierName")
    @Mapping(source = "project", target = "projectName", qualifiedByName = "toProjectName")
    public abstract EquipmentExcelDto toExcelDto(Equipment equipment);

    public abstract EquipmentQrDto toQrDto(Equipment equipment);

    @Named("toUnitName")
    public String toUnitName(EquipmentUnit unit){
        return unit == null ? null : unit.getName();
    }

    @Named("toDepartmentName")
    public String toDepartmentName(Department department) {
        return department == null ? null : department.getName();
    }

    @Named("toCategoryName")
    public String toCategoryName(EquipmentCategory category) {
        return category == null ? null : category.getName();
    }

    @Named("toSupplierName")
    public String toSupplierName(Supplier supplier) {
        return supplier == null ? null : supplier.getName();
    }

    @Named("toProjectName")
    public String toProjectName(Project project) {
        return project == null ? null : project.getName();
    }

    @Named("toCategoryGroupName")
    public String toCategoryGroupName(EquipmentCategory category) {
        return category == null ? null : category.getGroup().getName();
    }

    @Mapping(
            target = "imageIds",
            expression = "java(fileStorageService.getAllFileIdsOfAnEntity(equipment.getClass().getSimpleName(),equipment.getId(),com.mdm.equipmentservice.model.entity.FileDescription.IMAGE))"
    )
    public abstract EquipmentListDto toListDto(Equipment equipment);

    public abstract EquipmentListRepairDto toListRepairDto(Equipment equipment);

    public abstract EquipmentListMaintenanceDto toListMaintenanceDto(Equipment equipment);

    public abstract EquipmentListInspectionDto toListInspectionDto(Equipment equipment);

    public abstract EquipmentListLiquidationDto toListLiquidationDto(Equipment equipment);

    public abstract EquipmentListTransferDto toListTransferDto(Equipment equipment);

    public abstract EquipmentListHandoverDto toListHandoverDto(Equipment equipment);

    public abstract EquipmentListReportBrokenDto toListReportBrokenDto(Equipment equipment);

    @Mapping(target = "department", source = "handoverTicket.department")
    @Mapping(target = "status", expression = "java(EquipmentStatus.PENDING_HANDOVER)")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "handoverTickets", expression = "java(CollectionUtils.isEmpty(equipment.getHandoverTickets()) ? List.of(handoverTicket) : (List<HandoverTicket>) CollectionUtils.union(equipment.getHandoverTickets(), List.of(handoverTicket)))")
    public abstract Equipment createHandoverTicket(@MappingTarget Equipment equipment,
                                                   CreateHandoverTicketForm createHandoverTicketForm, HandoverTicket handoverTicket);

    @Mapping(target = "status", expression = "java(EquipmentStatus.PENDING_ACCEPT_LIQUIDATION)")
    @Mapping(
            target = "liquidationTickets",
            expression = "java(CollectionUtils.isEmpty(equipment.getLiquidationTickets()) ? List.of(liquidationTicket) : (List<LiquidationTicket>) CollectionUtils.union(equipment.getLiquidationTickets(), List.of(liquidationTicket)))"
    )
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Equipment addLiquidationTicket(@MappingTarget Equipment equipment, LiquidationTicket liquidationTicket);

    @Mapping(target = "status", expression = "java(acceptHandoverTicketForm.getIsApproved() ? EquipmentStatus.IN_USE : EquipmentStatus.NEW)")
    @Mapping(target = "department", expression = "java(acceptHandoverTicketForm.getIsApproved() ? equipment.getDepartment() : null)")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Equipment acceptHandoverTicket(@MappingTarget Equipment equipment, AcceptHandoverTicketForm acceptHandoverTicketForm);


    @Mapping(
            target = "reportBrokenTickets",
            expression = "java(equipment.getReportBrokenTickets() == null ? List.of(reportBrokenTicket) : (List<ReportBrokenTicket>) CollectionUtils.union(equipment.getReportBrokenTickets(), List.of(reportBrokenTicket)))"
    )
    @Mapping(target = "status", expression = "java(EquipmentStatus.PENDING_REPORT_BROKEN)")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Equipment createReportBrokenTicket(@MappingTarget Equipment equipment, ReportBrokenTicket reportBrokenTicket,
                                                       CreateReportBrokenTicketForm createReportBrokenTicketForm);


    @Mapping(target = "status", expression = "java(acceptReportBrokenTicketForm.getIsApproved() ? EquipmentStatus.BROKEN : EquipmentStatus.IN_USE)")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Equipment acceptReportBrokenTicket(@MappingTarget Equipment equipment, AcceptReportBrokenTicketForm acceptReportBrokenTicketForm);

    @Mapping(
            target = "repairTickets",
            expression = "java(equipment.getRepairTickets() == null ? List.of(repairTicket) : (List<RepairTicket>) CollectionUtils.union(equipment.getRepairTickets(), List.of(repairTicket)))"
    )
    @Mapping(target = "status", expression = "java(EquipmentStatus.PENDING_ACCEPT_REPAIR)")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Equipment createRepairTicket(@MappingTarget Equipment equipment, RepairTicket repairTicket, CreateRepairTicketForm createRepairTicketForm);

    @Mapping(target = "status", expression = "java(acceptRepairTicketForm.getIsAccepted() ? EquipmentStatus.REPAIRING : EquipmentStatus.BROKEN)")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Equipment acceptRepairTicket(@MappingTarget Equipment equipment, AcceptRepairTicketForm acceptRepairTicketForm);
}
