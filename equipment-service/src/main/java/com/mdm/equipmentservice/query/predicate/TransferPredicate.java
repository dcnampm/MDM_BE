package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.QTransferTicket;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import com.mdm.equipmentservice.query.param.GetTransfersQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

public class TransferPredicate {
    private static final QTransferTicket transferTicket = QTransferTicket.transferTicket;

    public static BooleanBuilder getTransferPredicate(GetTransfersQueryParam getTransfersQueryParam) {
        BooleanBuilder predicate = new BooleanBuilder();
        return predicate.and(matchEquipmentAttributes(getTransfersQueryParam.getKeyword()))
                .and(matchTicketStatus(getTransfersQueryParam.getStatus()))
                .and(matchEquipmentsForTransfering());
    }

    public static BooleanExpression matchEquipmentAttributes(String keyword) {
        return StringUtils.isNotBlank(keyword) ? transferTicket.equipment.name.containsIgnoreCase(keyword)
                .or(transferTicket.equipment.code.containsIgnoreCase(keyword))
                .or(transferTicket.equipment.hashCode.containsIgnoreCase(keyword))
                .or(transferTicket.equipment.model.containsIgnoreCase(keyword))
                .or(transferTicket.equipment.serial.containsIgnoreCase(keyword)) : null;
    }

    public static BooleanExpression matchEquipmentsForTransfering() {
        return transferTicket.equipment.status.in(EquipmentStatus.IN_USE, EquipmentStatus.PENDING_TRANSFER);
    }

    public static BooleanExpression matchTicketStatus(TicketStatus status) {
        return status != null ? transferTicket.status.eq(status) : null;
    }
}
