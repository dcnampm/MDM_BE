package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.QEquipmentGroup;
import com.mdm.equipmentservice.query.param.GetEquipmentGroupsQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

public class EquipmentGroupPredicate {
    private static final QEquipmentGroup equipmentGroup = QEquipmentGroup.equipmentGroup;
    public static Predicate getEquipmentGroupPredicate(GetEquipmentGroupsQueryParam getEquipmentGroupsQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getEquipmentGroupsQueryParam.getKeyword()));
                
    }

    public static Predicate commonAttributesContainKeyword(String keyword) {
        return StringUtils.isNotBlank(keyword) ? equipmentGroup.name.containsIgnoreCase(keyword)
                .or(equipmentGroup.note.containsIgnoreCase(keyword))
                .or(equipmentGroup.note.containsIgnoreCase(keyword))
                .or(equipmentGroup.alias.containsIgnoreCase(keyword)) : null;
    }
}
