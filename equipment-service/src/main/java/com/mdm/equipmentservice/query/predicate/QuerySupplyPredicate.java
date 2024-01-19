package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.QSupply;
import com.mdm.equipmentservice.query.param.GetSupplyQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

//TODO: còn thếu
public class QuerySupplyPredicate {
    private static final QSupply supply = QSupply.supply;

    public static Predicate getAllPredicate(GetSupplyQueryParam getSupplyQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributeContainKeyword(getSupplyQueryParam.getKeyword()));
    }

    public static BooleanExpression commonAttributeContainKeyword(String keyword) {
        return StringUtils.isNotBlank(keyword) ? supply.name.containsIgnoreCase(keyword) : null;
    }


}
