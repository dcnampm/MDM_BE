package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.QRole;
import com.mdm.equipmentservice.query.param.GetRolesQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

public class RolePredicate {

    private static final QRole qRole = QRole.role;

    public static Predicate getRolePredicate(GetRolesQueryParam getRolesQueryParam) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(matchName(getRolesQueryParam.getKeyword()));
        return booleanBuilder;
    }

    public static Predicate matchName(String name) {
        return StringUtils.isBlank(name) ? null : qRole.name.containsIgnoreCase(name);
    }

}
