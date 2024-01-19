package com.mdm.equipmentservice.query.predicate;

import com.mdm.equipmentservice.model.entity.QProject;
import com.mdm.equipmentservice.query.param.GetProjectsQueryParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

public class QueryProjectPredicate {

    private static final QProject project = QProject.project;

    public static BooleanBuilder getProjectsPredicate(final GetProjectsQueryParam getProjectsQueryParam) {
        BooleanBuilder predicate = new BooleanBuilder();

        predicate.and(matchCommonAttributes(getProjectsQueryParam.getKeyword()));
        return predicate;
    }

    public static BooleanExpression matchCommonAttributes(String keyword) {
        return StringUtils.isNotBlank(keyword) ? project.name.containsIgnoreCase(keyword)
                .or(project.fundingSource.containsIgnoreCase(keyword)) : null;
    }

}
