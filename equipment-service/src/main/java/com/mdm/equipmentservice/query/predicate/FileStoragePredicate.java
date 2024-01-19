package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.QFileStorage;
import com.mdm.equipmentservice.query.param.GetFileStoragesQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class FileStoragePredicate {
    private static final QFileStorage fileStorage = QFileStorage.fileStorage;

    public static Predicate getFileStoragePredicate(GetFileStoragesQueryParam getFileStoragesQueryParam) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        return booleanBuilder.and(matchFileDescription(getFileStoragesQueryParam.getFileDescription()))
                .and(matchAssociatedEntityType(getFileStoragesQueryParam.getAssociatedEntityType()))
                .and(matchAssociatedEntityId(getFileStoragesQueryParam.getAssociatedEntityId()));
    }

    public static Predicate matchAssociatedEntityId(Long associatedEntityId) {
        return associatedEntityId != null ? fileStorage.associatedEntityId.eq(associatedEntityId) : null;
    }

    public static Predicate matchAssociatedEntityType(String associatedEntityType) {
        return StringUtils.isNotBlank(associatedEntityType) ? fileStorage.associatedEntityType.eq(associatedEntityType) : null;
    }

    public static BooleanExpression matchFileDescription(FileDescription fileDescription) {
        return fileDescription != null ? fileStorage.description.eq(fileDescription) : null;
    }

    public static BooleanExpression whereIdIn(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return fileStorage.id.in(ids);
    }
}
