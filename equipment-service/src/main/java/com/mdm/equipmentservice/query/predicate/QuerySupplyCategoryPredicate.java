package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.QSupplyCategory;
import com.mdm.equipmentservice.query.param.GetSupplyCategoriesQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

public class QuerySupplyCategoryPredicate {

    private static final QSupplyCategory qSupplyCategory = QSupplyCategory.supplyCategory;

    public static Predicate getAllSupplyCategoriesPredicate(GetSupplyCategoriesQueryParam getSupplyCategoriesQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getSupplyCategoriesQueryParam.getKeyword()));
    }

    public static BooleanExpression commonAttributesContainKeyword(String keyword) {
        return keyword != null ? qSupplyCategory.name.containsIgnoreCase(keyword)
                .or(qSupplyCategory.alias.containsIgnoreCase(keyword))
                .or(qSupplyCategory.note.containsIgnoreCase(keyword)) : null;
    }

}
