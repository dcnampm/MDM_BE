package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.NotificationType;
import com.mdm.equipmentservice.model.entity.QNotificationsFromMgdb;
import com.mdm.equipmentservice.query.param.GetNotificationQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

public class NotificationPredicate {

    private static final QNotificationsFromMgdb notification = QNotificationsFromMgdb.notificationsFromMgdb;

    public static Predicate getNotificationPredicate(final GetNotificationQueryParam getNotificationQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(matchNotificationType(getNotificationQueryParam.getNotificationType()))
                .and(matchEquipmentId(getNotificationQueryParam.getEquipmentId()))
                .and(matchIsDeleted(getNotificationQueryParam.getIsDeleted()))
                ;
    }

    public static BooleanExpression matchNotificationType(NotificationType notificationType) {
        return notificationType != null ? notification.notificationType.eq(notificationType) : null;
    }

    public static BooleanExpression matchEquipmentId(Long equipmentId) {
        return equipmentId != null ? notification.equipmentId.eq(equipmentId) : null;
    }

    public static BooleanExpression matchIsDeleted(Boolean isDeleted) {
        return isDeleted != null ? notification.isDeleted.eq(isDeleted) : null;
    }
}