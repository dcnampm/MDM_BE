package com.mdm.equipmentservice.query.predicate;

//import com.mdm.equipmentservice.model.entity.QEquipmentCategory;
import com.mdm.equipmentservice.model.entity.QEquipmentCategory;
import com.mdm.equipmentservice.query.param.GetEquipmentCategoriesQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class EquipmentCategoryPredicate {
    private static final QEquipmentCategory equipmentCategory = QEquipmentCategory.equipmentCategory;

    public static Predicate getEquipmentCategoryPredicate(GetEquipmentCategoriesQueryParam getEquipmentCategoriesQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getEquipmentCategoriesQueryParam.getKeyword()))
                .and(matchGroupId(getEquipmentCategoriesQueryParam.getGroupId()));
    }

    public static Predicate commonAttributesContainKeyword(String keyword) {
        return StringUtils.isNotBlank(keyword) ? equipmentCategory.name.containsIgnoreCase(keyword)
                .or(equipmentCategory.note.containsIgnoreCase(keyword))
                .or(equipmentCategory.note.containsIgnoreCase(keyword))
                .or(equipmentCategory.alias.containsIgnoreCase(keyword)) : null;
    }

    public static Predicate matchGroupId(Long groupId) {
        return ObjectUtils.isNotEmpty(groupId) ? equipmentCategory.group.id.eq(groupId) : null;
    }
}
