package com.mdm.equipmentservice.model.dto.base;

import com.mdm.equipmentservice.model.entity.EquipmentCategory;
import com.mdm.equipmentservice.model.entity.EquipmentGroup;
import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.RiskLevel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class StatisticDashboardDto {
    private List<CountByDepartment> countByDepartment;

    private List<CountByRiskLevel> countByRiskLevels;

    private List<CountByEquipmentStatus> countByEquipmentStatuses;

    private List<CountByDepartment> countRepairingByDepartment;

    private List<CountByDepartment> countBrokenByDepartment;

    private List<CountByDepartmentAndStatus> countEquipmentByDepartmentAndStatus;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CountByDepartment {
        private Long departmentId;

        private String departmentName;

        private Long count;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    public static class CountByDepartmentAndStatus extends CountByDepartment {
        private Map<EquipmentStatus, Long> countByEquipmentStatus;

        public CountByDepartmentAndStatus(Long departmentId, String departmentName, Long count, Map<EquipmentStatus, Long> countByEquipmentStatuses) {
            super(departmentId, departmentName, count);
            this.countByEquipmentStatus = countByEquipmentStatuses;
        }
    }

    @AllArgsConstructor
    @Data
    public static class CountByRiskLevel {
        private RiskLevel riskLevel;

        private Long count;
    }

    @AllArgsConstructor
    @Data
    public static class CountByEquipmentStatus {
        private EquipmentStatus status;

        private Long count;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CountByGroup {
        private Long groupId;

        private String groupName;

        private Long count;
    }

    @AllArgsConstructor
    @Data
    public static class CountByCategory {
        private Long categoryId;

        private String categoryName;

        private Long count;
    }

    @AllArgsConstructor
    @Data
    public static class CountByGroupAndCategory extends CountByGroup {
        private List<CountByCategory> countByCategoryList;
        public CountByGroupAndCategory(Long groupId, String groupName, Long count, List<CountByCategory> list) {
            super(groupId, groupName, count);
            this.countByCategoryList = list;
        }
    }
}
