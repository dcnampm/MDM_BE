package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.QEquipmentUnit;
import com.mdm.equipmentservice.query.param.GetEquipmentUnitsQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

public class QueryEquipmentUnitPredicate {

    private static final QEquipmentUnit equipmentUnit = QEquipmentUnit.equipmentUnit;

    public static BooleanBuilder getEquipmentUnitsPredicate(final GetEquipmentUnitsQueryParam getEquipmentUnitsQueryParam) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(matchKeyword(getEquipmentUnitsQueryParam.getKeyword()));
        return booleanBuilder;
    }

    private static BooleanExpression matchKeyword(String keyword) {
        return StringUtils.isNotBlank(keyword) ? equipmentUnit.name.containsIgnoreCase(keyword) : null;
    }

}
