package com.mdm.equipmentservice.query.predicate;

public class MaintenanceTicketPredicate {
/*
    private static final QMaintenanceTicket maintenanceTicket = QMaintenanceTicket.maintenanceTicket;

    public static BooleanBuilder getMaintenanceTicketPredicate(GetEquipmentsForMaintenanceQueryParam getEquipmentsForMaintenanceQueryParam) {

        BooleanBuilder predicate = new BooleanBuilder();
        return predicate.and(equipmentMatchCommonAttributes(getEquipmentsForMaintenanceQueryParam.getKeyword()))
                .and(matchEquipmentStatus(getEquipmentsForMaintenanceQueryParam.getEquipmentStatus()))
                .and(equipmentStatusMustBe_InUse_Or_UnderMaintenance())
                .and(lastTimeBetween(getEquipmentsForMaintenanceQueryParam.getLastTimeFrom(), getEquipmentsForMaintenanceQueryParam.getLastTimeTo()))
                .and(matchCategoryId(getEquipmentsForMaintenanceQueryParam.getCategoryId()))
                .and(matchDepartment(getEquipmentsForMaintenanceQueryParam.getDepartmentId()))
                .and(matchRegularMaintenance(getEquipmentsForMaintenanceQueryParam.getRegularMaintenance()));
    }

    public static BooleanExpression equipmentMatchCommonAttributes(String keyword) {
        return StringUtils.isNotBlank(keyword) ? maintenanceTicket.equipment.name.containsIgnoreCase(keyword)
                .or(maintenanceTicket.equipment.code.containsIgnoreCase(keyword))
                .or(maintenanceTicket.equipment.model.containsIgnoreCase(keyword))
                .or(maintenanceTicket.equipment.serial.containsIgnoreCase(keyword))
                .or(maintenanceTicket.equipment.hashCode.containsIgnoreCase(keyword))
                .or(maintenanceTicket.equipment.manufacturer.containsIgnoreCase(keyword))
                .or(maintenanceTicket.equipment.manufacturingCountry.containsIgnoreCase(keyword)) : null;
    }

    public static BooleanExpression matchEquipmentStatus(EquipmentStatus equipmentStatus) {
        return equipmentStatus == null ? null : maintenanceTicket.equipment.status.eq(equipmentStatus);
    }

    public static BooleanExpression equipmentStatusMustBe_InUse_Or_UnderMaintenance() {
        return maintenanceTicket.equipment.status.in(EquipmentStatus.IN_USE, EquipmentStatus.UNDER_MAINTENANCE);
    }

    public static BooleanExpression matchCategoryId(Long categoryId) {
        return categoryId == null ? null : maintenanceTicket.equipment.category.id.eq(categoryId);
    }

    public static BooleanExpression lastTimeBetween(LocalDateTime lastTimeFrom, LocalDateTime lastTimeTo) {
        return CommonUtil.validateLocalDateTimeBetween(lastTimeFrom, lastTimeTo) ? maintenanceTicket.lastTime.between(lastTimeFrom, lastTimeTo) : null;
    }


    public static BooleanExpression matchDepartment(Long departmentId) {
        return departmentId == null ? null : maintenanceTicket.equipment.department.id.eq(departmentId);
    }

    public static BooleanExpression matchRegularMaintenance(Integer matchRegularMaintenance) {
        return matchRegularMaintenance == null ? null : maintenanceTicket.equipment.matchRegularMaintenance.eq(matchRegularMaintenance);
    }
*/
}
