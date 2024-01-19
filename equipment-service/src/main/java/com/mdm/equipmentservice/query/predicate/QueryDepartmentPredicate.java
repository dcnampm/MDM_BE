package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.DepartmentActiveStatus;
import com.mdm.equipmentservice.model.entity.QDepartment;
import com.mdm.equipmentservice.query.param.GetDepartmentsQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class QueryDepartmentPredicate {
    private static final QDepartment department = QDepartment.department;

    public static Predicate getAllDepartmentsPredicate(GetDepartmentsQueryParam getDepartmentsQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainKeyword(getDepartmentsQueryParam.getKeyword()))
                .and(matchActiveStatus(getDepartmentsQueryParam.getActiveStatus()))
                .and(matchContactPerson(getDepartmentsQueryParam.getContactPersonId()))
                .and(matchHeadOfDepartment(getDepartmentsQueryParam.getHeadOfDepartmentId()))
                .and(matchChiefNurse(getDepartmentsQueryParam.getChiefNurseId()))
                .and(matchManagerId(getDepartmentsQueryParam.getManagerId()));
    }

    public static BooleanExpression commonAttributesContainKeyword(String keyword) {
        return StringUtils.isNotBlank(keyword) ? department.name.containsIgnoreCase(keyword)
                .or(department.alias.containsIgnoreCase(keyword))
                .or(department.phone.containsIgnoreCase(keyword))
                .or(department.email.containsIgnoreCase(keyword))
                .or(department.address.containsIgnoreCase(keyword)) : null;
    }

    public static BooleanExpression matchActiveStatus(DepartmentActiveStatus activeStatus) {
        return activeStatus != null ? department.activeStatus.eq(activeStatus) : null;
    }

    public static BooleanExpression matchContactPerson(Long contactPersonId) {
        return ObjectUtils.isNotEmpty(contactPersonId) ? department.contactPerson.id.eq(contactPersonId) : null;
    }

    public static BooleanExpression matchHeadOfDepartment(Long headOfDepartmentId) {
        return ObjectUtils.isNotEmpty(headOfDepartmentId) ? department.headOfDepartment.id.eq(headOfDepartmentId) : null;
    }

    public static BooleanExpression matchChiefNurse(Long chiefNurseId) {
        return ObjectUtils.isNotEmpty(chiefNurseId) ? department.chiefNurse.id.eq(chiefNurseId) : null;
    }

    public static BooleanExpression matchManagerId(Long managerId) {
        return ObjectUtils.isNotEmpty(managerId) ? department.manager.id.eq(managerId) : null;
    }
}
