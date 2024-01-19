package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.QSupplier;
import com.mdm.equipmentservice.query.param.GetSuppliersQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

public class QuerySupplierPredicate {
    private static final QSupplier supplier = QSupplier.supplier;

    public static Predicate getAllSuppliersPredicate(GetSuppliersQueryParam getSuppliersQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getSuppliersQueryParam.getKeyword())).and(hasService(getSuppliersQueryParam.getServiceId()));
    }

    public static BooleanExpression commonAttributesContainKeyword(String keyword) {
        return StringUtils.isNotBlank(keyword) ? supplier.name.containsIgnoreCase(keyword)
                .or(supplier.note.containsIgnoreCase(keyword))
                .or(supplier.taxCode.containsIgnoreCase(keyword))
                .or(supplier.email.containsIgnoreCase(keyword))
                .or(supplier.hotline.containsIgnoreCase(keyword))
                .or(supplier.fax.containsIgnoreCase(keyword))
                .or(supplier.website.containsIgnoreCase(keyword))
                .or(supplier.address.containsIgnoreCase(keyword)) : null;
    }
    public static BooleanExpression hasService(Long serviceId) {
        return serviceId != null ? supplier.services.any().id.eq(serviceId) : null;
    }
}
