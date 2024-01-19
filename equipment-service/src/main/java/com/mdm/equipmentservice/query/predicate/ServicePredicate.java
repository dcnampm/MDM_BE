package com.mdm.equipmentservice.query.predicate;


import com.mdm.equipmentservice.model.entity.QService;
import com.mdm.equipmentservice.query.param.GetServicesQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

public class ServicePredicate {

    private static final QService service = QService.service;

    public static Predicate getServicePredicate(GetServicesQueryParam getServicesQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getServicesQueryParam.getKeyword()));

    }

    public static Predicate commonAttributesContainKeyword(String keyword) {
        return StringUtils.isNotBlank(keyword) ? service.name.containsIgnoreCase(keyword)
                .or(service.note.containsIgnoreCase(keyword)) : null;
    }
}
