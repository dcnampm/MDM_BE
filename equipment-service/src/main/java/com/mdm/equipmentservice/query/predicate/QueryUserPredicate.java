package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.QUser;
import com.mdm.equipmentservice.model.entity.WorkingStatus;
import com.mdm.equipmentservice.query.param.GetUsersQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

public class QueryUserPredicate {

    private static final QUser user = QUser.user;

    public static Predicate getAllUsersPredicate(GetUsersQueryParam getUsersQueryParam) {
        BooleanBuilder where = new BooleanBuilder();
        return where.and(commonAttributesContainsKeyword(getUsersQueryParam.getKeyword()))
                .and(matchGender(getUsersQueryParam.getGender()))
                .and(roleIn(getUsersQueryParam.getRoleIds()))
                .and(matchWorkingStatus(getUsersQueryParam.getWorkingStatus()))
                .and(userInDepartment(getUsersQueryParam.getDepartmentId()))
                .and(birthDayInRange(getUsersQueryParam.getBirthdayQueryStart(), getUsersQueryParam.getBirthdayQueryEnd()))
                .and(isEnabled(getUsersQueryParam.getEnabled()));
    }

    public static BooleanExpression commonAttributesContainsKeyword(String keyword) {
        return StringUtils.isNotBlank(keyword) ? user.name.containsIgnoreCase(keyword)
                .or(user.username.containsIgnoreCase(keyword))
                .or(user.address.containsIgnoreCase(keyword))
                .or(user.email.containsIgnoreCase(keyword))
                .or(user.phone.containsIgnoreCase(keyword)) : null;
    }

    public static BooleanExpression matchGender(Boolean gender) {
        return gender != null ? user.gender.eq(gender) : null;
    }

    public static BooleanExpression roleIn(Long[] roleIds) {
        return roleIds != null && roleIds.length > 0 ? user.role.id.in(roleIds) : null;
    }
public static BooleanExpression roleNameIn(String[] roleNames) {
        return roleNames != null && roleNames.length > 0 ? user.role.name.in(roleNames) : null;
    }
    public static BooleanExpression matchWorkingStatus(WorkingStatus workingStatus) {
        return workingStatus != null ? user.workingStatus.eq(workingStatus) : null;
    }

    public static BooleanExpression userInDepartment(Long departmentId) {
        return ObjectUtils.isNotEmpty(departmentId) ? user.department.id.eq(departmentId) : null;
    }

    public static BooleanExpression birthDayInRange(LocalDateTime from, LocalDateTime to) {
        return from != null && to != null ? user.birthday.between(from, to) : null;
    }

    public static BooleanExpression isEnabled(Boolean enabled) {
        return enabled != null ? user.enabled.eq(enabled) : null;
    }
}
