package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.query.param.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.Year;

import static com.mdm.equipmentservice.util.CommonUtil.*;


public class EquipmentPredicate {

    private static final QEquipment equipment = QEquipment.equipment;

    public static BooleanBuilder getEquipmentsPredicate(final GetEquipmentsQueryParam getEquipmentsQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where
                .and(matchStatus(getEquipmentsQueryParam.getStatus()))
                .and(matchRiskLevel(getEquipmentsQueryParam.getRiskLevel()))
                .and(warehouseImportDateBetween(getEquipmentsQueryParam.getWarehouseImportDateFrom(), getEquipmentsQueryParam.getWarehouseImportDateTo()))
                .and(yearOfManufactureBetween(getEquipmentsQueryParam.getYearOfManufactureFrom(), getEquipmentsQueryParam.getYearOfManufactureTo()))
                .and(yearInUseBetween(getEquipmentsQueryParam.getYearInUseFrom(), getEquipmentsQueryParam.getYearInUseTo()))
                .and(importPriceBetween(getEquipmentsQueryParam.getImportPriceFrom(), getEquipmentsQueryParam.getImportPriceTo()))
                .and(initialValueBetween(getEquipmentsQueryParam.getInitialValueFrom(), getEquipmentsQueryParam.getInitialValueTo()))
                .and(matchSupplierId(getEquipmentsQueryParam.getSupplierId()))
                .and(matchCategoryId(getEquipmentsQueryParam.getCategoryId()))
                .and(matchDepartmentId(getEquipmentsQueryParam.getDepartmentId()))
                .and(matchProjectId(getEquipmentsQueryParam.getProjectId()))
                .and(jointVentureContractExpirationDateBetween(
                        getEquipmentsQueryParam.getJointVentureContractExpirationDateFrom(),
                        getEquipmentsQueryParam.getJointVentureContractExpirationDateTo()
                ))
                .and(warrantyExpirationDateBetween(
                        getEquipmentsQueryParam.getWarrantyExpirationDateFrom(),
                        getEquipmentsQueryParam.getWarrantyExpirationDateTo()
                ))
                .and(lastMaintenanceDateBetween(
                        getEquipmentsQueryParam.getLastMaintenanceDateFrom(),
                        getEquipmentsQueryParam.getLastMaintenanceDateTo()
                ))
                .and(nextMaintenanceDateBetween(
                        getEquipmentsQueryParam.getNextMaintenanceDateFrom(),
                        getEquipmentsQueryParam.getNextMaintenanceDateTo()
                ))
                .and(lastInspectionDateBetween(
                        getEquipmentsQueryParam.getLastInspectionDateFrom(),
                        getEquipmentsQueryParam.getLastInspectionDateTo()
                ))
                .and(matchGroupId(getEquipmentsQueryParam.getGroupId()))
                .and(nextInspectionDateBetween(
                        getEquipmentsQueryParam.getNextInspectionDateFrom(),
                        getEquipmentsQueryParam.getNextInspectionDateTo()
                ))
                .and(matchRegularMaintenance(getEquipmentsQueryParam.getRegularMaintenance()))
                .and(matchRegularInspection(getEquipmentsQueryParam.getRegularInspection()))
                .and(commonAttributesContainKeyword(getEquipmentsQueryParam.getKeyword()));
    }

    /**
     * Get all equipments whose status is BROKEN or REPAIRING
     */
    public static BooleanBuilder getAllEquipmentsForRepairingPredicate(final GetEquipmentsQueryParam getEquipmentsQueryParam) {
        return getEquipmentsPredicate(getEquipmentsQueryParam).and(statusIn(
                EquipmentStatus.BROKEN,
                EquipmentStatus.REPAIRING,
                EquipmentStatus.PENDING_ACCEPT_REPAIR,
                EquipmentStatus.PENDING_ACCEPTANCE_TESTING
        ));
    }

    public static BooleanBuilder getAllEquipmentsForMaintenancePredicate(final GetEquipmentsForMaintenanceQueryParam getEquipmentsForMaintenanceQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getEquipmentsForMaintenanceQueryParam.getKeyword()))
                .and(matchStatus(getEquipmentsForMaintenanceQueryParam.getEquipmentStatus()))
                .and(matchDepartmentId(getEquipmentsForMaintenanceQueryParam.getDepartmentId()))
                .and(matchCategoryId(getEquipmentsForMaintenanceQueryParam.getCategoryId()))
                .and(lastTimeMaintenanceBetween(getEquipmentsForMaintenanceQueryParam.getLastTimeFrom(), getEquipmentsForMaintenanceQueryParam.getLastTimeTo()))
                .and(nextTimeMaintenanceBetween(getEquipmentsForMaintenanceQueryParam.getNextTimeFrom(), getEquipmentsForMaintenanceQueryParam.getNextTimeTo()))
                .and(matchRegularMaintenance(getEquipmentsForMaintenanceQueryParam.getRegularMaintenance()))
                .and(regularMaintenanceGreaterThanZero())
                .and(matchGroupId(getEquipmentsForMaintenanceQueryParam.getGroupId()))
                .and(statusIn(EquipmentStatus.IN_USE, EquipmentStatus.UNDER_MAINTENANCE, EquipmentStatus.PENDING_ACCEPT_MAINTENANCE));
    }

    public static BooleanBuilder getAllEquipmentsForRepairPredicate(final GetEquipmentsForRepairQueryParam getEquipmentsForRepairQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getEquipmentsForRepairQueryParam.getKeyword()))
                .and(matchStatus(getEquipmentsForRepairQueryParam.getEquipmentStatus()))
                .and(matchDepartmentId(getEquipmentsForRepairQueryParam.getDepartmentId()))
                .and(matchCategoryId(getEquipmentsForRepairQueryParam.getCategoryId()))
                .and(matchGroupId(getEquipmentsForRepairQueryParam.getGroupId()))
                .and(statusIn(
                        EquipmentStatus.BROKEN,
                        EquipmentStatus.REPAIRING,
                        EquipmentStatus.PENDING_ACCEPT_REPAIR,
                        EquipmentStatus.PENDING_ACCEPTANCE_TESTING
                ));
    }

    public static BooleanBuilder getAllEquipmentsForReportBrokenPredicate(final GetEquipmentsForReportBrokenQueryParam getEquipmentsForReportBrokenQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getEquipmentsForReportBrokenQueryParam.getKeyword()))
                .and(matchStatus(getEquipmentsForReportBrokenQueryParam.getEquipmentStatus()))
                .and(matchDepartmentId(getEquipmentsForReportBrokenQueryParam.getDepartmentId()))
                .and(matchCategoryId(getEquipmentsForReportBrokenQueryParam.getCategoryId()))
                .and(matchGroupId(getEquipmentsForReportBrokenQueryParam.getGroupId()))
                .and(createdDateBetween(
                        getEquipmentsForReportBrokenQueryParam.getReportBrokenDateFrom(),
                        getEquipmentsForReportBrokenQueryParam.getReportBrokenDateTo()
                ))
                .and(statusIn(EquipmentStatus.IN_USE, EquipmentStatus.PENDING_REPORT_BROKEN));
    }

    public static BooleanBuilder getAllEquipmentsForInspectionPredicate(final GetEquipmentsForInspectionQueryParam getEquipmentsForInspectionQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getEquipmentsForInspectionQueryParam.getKeyword()))
                .and(matchStatus(getEquipmentsForInspectionQueryParam.getEquipmentStatus()))
                .and(statusIn(EquipmentStatus.IN_USE, EquipmentStatus.UNDER_INSPECTION, EquipmentStatus.PENDING_ACCEPT_INSPECTION))
                .and(matchDepartmentId(getEquipmentsForInspectionQueryParam.getDepartmentId()))
                .and(matchCategoryId(getEquipmentsForInspectionQueryParam.getCategoryId()))
                .and(lastTimeInspectionBetween(getEquipmentsForInspectionQueryParam.getLastTimeFrom(), getEquipmentsForInspectionQueryParam.getLastTimeTo()))
                .and(nextTimeInspectionBetween(getEquipmentsForInspectionQueryParam.getNextTimeFrom(), getEquipmentsForInspectionQueryParam.getNextTimeTo()))
                .and(matchRegularInspection(getEquipmentsForInspectionQueryParam.getRegularInspection()))
                .and(matchGroupId(getEquipmentsForInspectionQueryParam.getGroupId()))
                .and(regularInspectionGreaterThanZero());
    }

    public static BooleanBuilder getAllEquipmentsForLiquidationPredicate(final GetEquipmentsForLiquidationQueryParam getEquipmentsForLiquidationQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getEquipmentsForLiquidationQueryParam.getKeyword()))
                .and(matchStatus(getEquipmentsForLiquidationQueryParam.getEquipmentStatus()))
                .and(statusIn(EquipmentStatus.INACTIVE, EquipmentStatus.PENDING_ACCEPT_LIQUIDATION, EquipmentStatus.LIQUIDATED))
                .and(matchDepartmentId(getEquipmentsForLiquidationQueryParam.getDepartmentId()))
                .and(matchCategoryId(getEquipmentsForLiquidationQueryParam.getCategoryId()))
                .and(liquidationDateBetween(
                        getEquipmentsForLiquidationQueryParam.getLiquidationDateFrom(),
                        getEquipmentsForLiquidationQueryParam.getLiquidationDateTo()
                ))
                .and(matchGroupId(getEquipmentsForLiquidationQueryParam.getGroupId()));
    }

    public static BooleanBuilder getAllEquipmentsForHandoverPredicate(final GetEquipmentsForHandoverQueryParam getEquipmentsForHandoverQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getEquipmentsForHandoverQueryParam.getKeyword()))
                .and(matchStatus(getEquipmentsForHandoverQueryParam.getEquipmentStatus()))
                .and(statusIn(EquipmentStatus.NEW, EquipmentStatus.PENDING_HANDOVER))
                .and(matchDepartmentId(getEquipmentsForHandoverQueryParam.getDepartmentId()))
                .and(matchCategoryId(getEquipmentsForHandoverQueryParam.getCategoryId()))
                .and(matchGroupId(getEquipmentsForHandoverQueryParam.getGroupId()))
                .and(handoverDateBetween(getEquipmentsForHandoverQueryParam.getHandoverDateFrom(), getEquipmentsForHandoverQueryParam.getHandoverDateTo()));
    }

    public static BooleanBuilder getAllEquipmentsForTransferPredicate(final GetEquipmentsForTransferQueryParam getEquipmentsForTransferQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getEquipmentsForTransferQueryParam.getKeyword()))
                .and(matchStatus(getEquipmentsForTransferQueryParam.getEquipmentStatus()))
                .and(statusIn(EquipmentStatus.IN_USE, EquipmentStatus.PENDING_TRANSFER))
                .and(matchDepartmentId(getEquipmentsForTransferQueryParam.getDepartmentId()))
                .and(matchCategoryId(getEquipmentsForTransferQueryParam.getCategoryId()))
                .and(matchGroupId(getEquipmentsForTransferQueryParam.getGroupId()));
    }

    public static BooleanExpression liquidationDateBetween(LocalDateTime liquidationDateFrom, LocalDateTime liquidationDateTo) {
        QLiquidationTicket qLiquidationTicket = equipment.liquidationTickets.any();
        return validateLocalDateTimeBetween(liquidationDateFrom, liquidationDateTo) ?
                qLiquidationTicket.liquidationDate.between(liquidationDateFrom, liquidationDateTo).and(qLiquidationTicket.status.eq(TicketStatus.ACCEPTED)) :
                null;
    }

    public static BooleanExpression lastTimeMaintenanceBetween(LocalDateTime lastTimeMaintenanceFrom, LocalDateTime lastTimeMaintenanceTo) {
        return validateLocalDateTimeBetween(lastTimeMaintenanceFrom, lastTimeMaintenanceTo) ?
                equipment.maintenanceTickets.any().maintenanceDate.between(lastTimeMaintenanceFrom, lastTimeMaintenanceTo) : null;
    }

    public static BooleanExpression lastTimeInspectionBetween(LocalDateTime lastTimeInspectionFrom, LocalDateTime lastTimeInspectionTo) {
        return validateLocalDateTimeBetween(lastTimeInspectionFrom, lastTimeInspectionTo) ?
                equipment.inspectionTickets.any().inspectionDate.between(lastTimeInspectionFrom, lastTimeInspectionTo) : null;
    }

    public static BooleanExpression lastInspectionDateBetween(LocalDateTime lastInspectionDateFrom, LocalDateTime lastInspectionDateTo) {
        return validateLocalDateTimeBetween(lastInspectionDateFrom, lastInspectionDateTo) ?
                equipment.lastInspectionDate.between(lastInspectionDateFrom, lastInspectionDateTo) : null;
    }

    public static BooleanExpression lastMaintenanceDateBetween(LocalDateTime lastMaintenanceDateFrom, LocalDateTime lastMaintenanceDateTo) {
        return validateLocalDateTimeBetween(lastMaintenanceDateFrom, lastMaintenanceDateTo) ?
                equipment.lastMaintenanceDate.between(lastMaintenanceDateFrom, lastMaintenanceDateTo) : null;
    }

    public static BooleanExpression nextInspectionDateBetween(LocalDateTime nextInspectionFrom, LocalDateTime nextInspectionTo) {
        return validateLocalDateTimeBetween(nextInspectionFrom, nextInspectionTo) ?
                Expressions.dateTimeOperation(LocalDateTime.class, Ops.DateTimeOps.ADD_MONTHS, equipment.lastInspectionDate, equipment.regularInspection)
                        .between(nextInspectionFrom, nextInspectionTo) : null;
    }

    public static BooleanExpression nextMaintenanceDateBetween(LocalDateTime nextMaintenanceFrom, LocalDateTime nextMaintenanceTo) {
        return validateLocalDateTimeBetween(nextMaintenanceFrom, nextMaintenanceTo) ?
                Expressions.dateTimeOperation(LocalDateTime.class, Ops.DateTimeOps.ADD_MONTHS, equipment.lastMaintenanceDate, equipment.regularMaintenance)
                        .between(nextMaintenanceFrom, nextMaintenanceTo) : null;
    }

    public static BooleanExpression nextTimeMaintenanceBetween(LocalDateTime nextTimeMaintenanceFrom, LocalDateTime nextTimeMaintenanceTo) {
        QMaintenanceTicket maintenanceTicket = equipment.maintenanceTickets.any();
        return validateLocalDateTimeBetween(nextTimeMaintenanceFrom, nextTimeMaintenanceTo) ?
                Expressions.dateTimeOperation(LocalDateTime.class, Ops.DateTimeOps.ADD_MONTHS, maintenanceTicket.maintenanceDate, equipment.regularMaintenance)
                        .between(nextTimeMaintenanceFrom, nextTimeMaintenanceTo) : null;
    }

    public static BooleanExpression nextTimeInspectionBetween(LocalDateTime nextTimeInspectionFrom, LocalDateTime nextTimeInspectionTo) {
        QInspectionTicket inspectionTicket = equipment.inspectionTickets.any();
        return validateLocalDateTimeBetween(nextTimeInspectionFrom, nextTimeInspectionTo) ?
                Expressions.dateTimeOperation(LocalDateTime.class, Ops.DateTimeOps.ADD_MONTHS, inspectionTicket.inspectionDate, equipment.regularInspection)
                        .between(nextTimeInspectionFrom, nextTimeInspectionTo) : null;
    }

    public static BooleanExpression matchRegularMaintenance(Integer regularMaintenance) {
        return regularMaintenance != null ? equipment.regularMaintenance.eq(regularMaintenance) : null;
    }


    public static BooleanExpression matchRegularInspection(Integer regularInspection) {
        return regularInspection != null ? equipment.regularInspection.eq(regularInspection) : null;
    }

    public static BooleanExpression commonAttributesContainKeyword(String keyword) {
        return StringUtils.isNotBlank(keyword) ? equipment.name.containsIgnoreCase(keyword)
                .or(equipment.code.containsIgnoreCase(keyword))
                .or(equipment.model.containsIgnoreCase(keyword))
                .or(equipment.serial.containsIgnoreCase(keyword))
                .or(equipment.hashCode.containsIgnoreCase(keyword))
                .or(equipment.manufacturer.containsIgnoreCase(keyword))
                .or(equipment.manufacturingCountry.containsIgnoreCase(keyword))
                .or(equipment.note.containsIgnoreCase(keyword)) : null;
    }

    public static BooleanExpression matchStatus(EquipmentStatus status) {
        return status != null ? equipment.status.eq(status) : null;
    }

    public static BooleanExpression matchRiskLevel(RiskLevel riskLevel) {
        return riskLevel != null ? equipment.riskLevel.eq(riskLevel) : null;
    }

    public static BooleanExpression warehouseImportDateBetween(LocalDateTime warehouseImportDateFrom, LocalDateTime warehouseImportDateTo) {
        return validateLocalDateTimeBetween(warehouseImportDateFrom, warehouseImportDateTo) ?
                equipment.warehouseImportDate.between(warehouseImportDateFrom, warehouseImportDateTo) : null;
    }

    public static BooleanExpression warrantyExpirationDateBetween(LocalDateTime warrantyExpirationDateFrom, LocalDateTime warrantyExpirationDateTo) {
        return validateLocalDateTimeBetween(warrantyExpirationDateFrom, warrantyExpirationDateTo) ?
                equipment.warrantyExpirationDate.between(warrantyExpirationDateFrom, warrantyExpirationDateTo) : null;
    }

    public static BooleanExpression jointVentureContractExpirationDateBetween(LocalDateTime jointVentureContractExpirationDate,
                                                                              LocalDateTime jointVentureExpirationDateTo) {
        return validateLocalDateTimeBetween(jointVentureContractExpirationDate, jointVentureExpirationDateTo) ?
                equipment.jointVentureContractExpirationDate.between(jointVentureContractExpirationDate, jointVentureExpirationDateTo) : null;
    }


    public static BooleanExpression handoverDateBetween(LocalDateTime handoverDateFrom, LocalDateTime handoverDateTo) {
        QHandoverTicket handoverTicket = equipment.handoverTickets.any();
        return validateLocalDateTimeBetween(handoverDateFrom, handoverDateTo) ?
                handoverTicket.handoverDate.between(handoverDateFrom, handoverDateTo) : null;
    }

    public static BooleanExpression createdDateBetween(LocalDateTime reportBrokenDateFrom, LocalDateTime reportBrokenDateTo) {
        return validateLocalDateTimeBetween(reportBrokenDateFrom, reportBrokenDateTo) ?
                equipment.reportBrokenTickets.any().createdDate.between(reportBrokenDateFrom, reportBrokenDateTo) : null;
    }

    public static BooleanExpression regularMaintenanceGreaterThanZero() {
        return equipment.regularMaintenance.gt(0);
    }


    public static BooleanExpression regularInspectionGreaterThanZero() {
        return equipment.regularInspection.gt(0);
    }

    public static BooleanExpression yearOfManufactureBetween(Year yearOfManufactureFrom, Year yearOfManufactureTo) {
        return validateYearBetween(yearOfManufactureFrom, yearOfManufactureTo) ?
                equipment.yearOfManufacture.between(yearOfManufactureFrom, yearOfManufactureTo) : null;
    }

    public static BooleanExpression yearInUseBetween(Year yearInUseFrom, Year yearInUseTo) {
        return validateYearBetween(yearInUseFrom, yearInUseTo) ? equipment.yearInUse.between(yearInUseFrom, yearInUseTo) : null;
    }

    public static BooleanExpression importPriceBetween(Double importPriceFrom, Double importPriceTo) {
        return validateIntegerBetween(importPriceFrom, importPriceTo) ? equipment.importPrice.between(importPriceFrom, importPriceTo) : null;
    }

    public static BooleanExpression initialValueBetween(Double initialValueFrom, Double initialValueTo) {
        return validateIntegerBetween(initialValueFrom, initialValueTo) ? equipment.importPrice.between(initialValueFrom, initialValueTo) : null;
    }

    public static BooleanExpression matchSupplierId(Long supplierId) {
        return supplierId != null ? equipment.supplier.id.eq(supplierId) : null;
    }

    public static BooleanExpression matchCategoryId(Long categoryId) {
        return ObjectUtils.isNotEmpty(categoryId) ? equipment.category.id.eq(categoryId) : null;
    }

    public static BooleanExpression matchGroupId(Long groupId) {
        return ObjectUtils.isNotEmpty(groupId) ? equipment.category.group.id.eq(groupId) : null;
    }

    public static BooleanExpression matchDepartmentId(Long departmentId) {
        return departmentId != null ? equipment.department.id.eq(departmentId) : null;
    }


    public static BooleanExpression statusIn(EquipmentStatus... statuses) {
        return ObjectUtils.isNotEmpty(statuses) ? equipment.status.in(statuses) : null;
    }

    public static BooleanExpression matchProjectId(Long projectId) {
        return ObjectUtils.isNotEmpty(projectId) ? equipment.project.id.eq(projectId) : null;
    }
}
