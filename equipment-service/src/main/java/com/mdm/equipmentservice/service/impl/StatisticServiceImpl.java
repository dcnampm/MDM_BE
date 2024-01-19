package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.model.dto.base.StatisticDashboardDto;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.model.repository.DepartmentRepository;
import com.mdm.equipmentservice.model.repository.EquipmentCategoryRepository;
import com.mdm.equipmentservice.model.repository.EquipmentGroupRepository;
import com.mdm.equipmentservice.model.repository.EquipmentRepository;
import com.mdm.equipmentservice.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.mdm.equipmentservice.query.predicate.EquipmentPredicate.*;

@Service
public class StatisticServiceImpl implements StatisticService {
    private final EquipmentRepository equipmentRepository;

    private final DepartmentRepository departmentRepository;

    private final EquipmentGroupRepository equipmentGroupRepository;

    private final EquipmentCategoryRepository equipmentCategoryRepository;

    @Autowired
    public StatisticServiceImpl(EquipmentRepository equipmentRepository, DepartmentRepository departmentRepository, EquipmentGroupRepository equipmentGroupRepository, EquipmentCategoryRepository equipmentCategoryRepository) {
        this.equipmentRepository = equipmentRepository;
        this.departmentRepository = departmentRepository;
        this.equipmentGroupRepository = equipmentGroupRepository;
        this.equipmentCategoryRepository = equipmentCategoryRepository;
    }

    private static final List<EquipmentStatus> listStatusStatistic = new ArrayList<>();

    static {
        listStatusStatistic.add(EquipmentStatus.NEW);
        listStatusStatistic.add(EquipmentStatus.IN_USE);
        listStatusStatistic.add(EquipmentStatus.INACTIVE);
        listStatusStatistic.add(EquipmentStatus.BROKEN);
        listStatusStatistic.add(EquipmentStatus.REPAIRING);
        listStatusStatistic.add(EquipmentStatus.LIQUIDATED);
    }

    @Override
    public StatisticDashboardDto getStatisticDashboard() {
        StatisticDashboardDto statisticDashboardDto = new StatisticDashboardDto();
        List<StatisticDashboardDto.CountByDepartment> countEquipmentByDepartmentList = new ArrayList<>();
        List<StatisticDashboardDto.CountByRiskLevel> countEquipmentByRiskLevelList = new ArrayList<>();
        List<StatisticDashboardDto.CountByEquipmentStatus> countEquipmentByStatusList = new ArrayList<>();
        List<StatisticDashboardDto.CountByDepartment> countRepairingByDepartmentList = new ArrayList<>();
        List<StatisticDashboardDto.CountByDepartment> countBrokenByDepartmentList = new ArrayList<>();
        List<StatisticDashboardDto.CountByDepartmentAndStatus> countEquipmentByDepartmentAndStatusList = new ArrayList<>();
        List<StatisticDashboardDto.CountByGroupAndCategory> countByEquipmentGroupAndCategoryList = new ArrayList<>();


        List<Department> allDepartments = departmentRepository.findAll();
        CompletableFuture<Void> countEquipmentByDepartmentFuture = CompletableFuture.runAsync(() -> allDepartments.parallelStream().forEach(department -> {
            //count equipment in each department

            countEquipmentByDepartmentList.add(new StatisticDashboardDto.CountByDepartment(
                    department.getId(),
                    department.getName(),
                    equipmentRepository.count(Objects.requireNonNull(matchDepartmentId(department.getId())))

            ));

            countEquipmentByDepartmentAndStatusList.add(new StatisticDashboardDto.CountByDepartmentAndStatus(
                    department.getId(),
                    department.getName(),
                    equipmentRepository.count(Objects.requireNonNull(matchDepartmentId(department.getId()))),
                    countEquipmentWithStatusesByDepartment(department.getId())
            ));
            //count equipment that has REPAIRING status in each department
            countRepairingByDepartmentList.add(new StatisticDashboardDto.CountByDepartment(
                    department.getId(),
                    department.getName(),
                    equipmentRepository.count(matchDepartmentId(department.getId()).and(matchStatus(EquipmentStatus.REPAIRING)))
//                    null
            ));
            //count equipment that has BROKEN status in each department
            countBrokenByDepartmentList.add(new StatisticDashboardDto.CountByDepartment(
                    department.getId(),
                    department.getName(),
                    equipmentRepository.count(matchDepartmentId(department.getId()).and(matchStatus(EquipmentStatus.BROKEN)))
//                    null
            ));

        }));
        CompletableFuture<Void> countEquipmentByRiskLevelFuture = CompletableFuture.runAsync(() -> Arrays.stream(RiskLevel.values()).forEach(riskLevel -> {
            //count equipment by risk level
            countEquipmentByRiskLevelList.add(new StatisticDashboardDto.CountByRiskLevel(riskLevel, equipmentRepository.count(matchRiskLevel(riskLevel))));
        }));
        CompletableFuture<Void> countEquipmentByStatusFuture = CompletableFuture.runAsync(() -> Arrays.stream(EquipmentStatus.values())
                .forEach(equipmentStatus -> {
                    //do not statistic equipment that has these statuses
                    if (statusNotIn(equipmentStatus)) {
                        return;
                    }
                    //count equipment by status
                    countEquipmentByStatusList.add(new StatisticDashboardDto.CountByEquipmentStatus(
                            equipmentStatus,
                            equipmentRepository.count(matchStatus(equipmentStatus))
                    ));
                }));

        CompletableFuture.allOf(countEquipmentByDepartmentFuture, countEquipmentByRiskLevelFuture, countEquipmentByStatusFuture).join();
        statisticDashboardDto.setCountBrokenByDepartment(countBrokenByDepartmentList);
        statisticDashboardDto.setCountRepairingByDepartment(countRepairingByDepartmentList);
        statisticDashboardDto.setCountByDepartment(countEquipmentByDepartmentList);
        statisticDashboardDto.setCountByRiskLevels(countEquipmentByRiskLevelList);
        statisticDashboardDto.setCountByEquipmentStatuses(countEquipmentByStatusList);
        statisticDashboardDto.setCountEquipmentByDepartmentAndStatus(countEquipmentByDepartmentAndStatusList);
        return statisticDashboardDto;
    }



    /**
     * Check if equipment status is not in these statuses
     */
    public static boolean statusNotIn(EquipmentStatus equipmentStatus) {
        return equipmentStatus.equals(EquipmentStatus.PENDING_HANDOVER) || equipmentStatus.equals(EquipmentStatus.PENDING_TRANSFER) ||
                equipmentStatus.equals(EquipmentStatus.PENDING_REPORT_BROKEN) || equipmentStatus.equals(EquipmentStatus.PENDING_ACCEPT_REPAIR) ||
                equipmentStatus.equals(EquipmentStatus.PENDING_ACCEPT_LIQUIDATION) || equipmentStatus.equals(EquipmentStatus.PENDING_ACCEPTANCE_TESTING) ||
                equipmentStatus.equals(EquipmentStatus.UNDER_MAINTENANCE) || equipmentStatus.equals(EquipmentStatus.PENDING_ACCEPT_MAINTENANCE) ||
                equipmentStatus.equals(EquipmentStatus.PENDING_ACCEPT_INSPECTION) || equipmentStatus.equals(EquipmentStatus.UNDER_INSPECTION);
    }





    @Override
    public List<StatisticDashboardDto.CountByGroupAndCategory> getStatisticByEquipmentGroup() {
        List<EquipmentGroup> equipmentGroupList = equipmentGroupRepository.findAll();
        List<StatisticDashboardDto.CountByGroupAndCategory> list = new ArrayList<>();
        equipmentGroupList.forEach(equipmentGroup -> {
            list.add(new StatisticDashboardDto.CountByGroupAndCategory(
                    equipmentGroup.getId(),
                    equipmentGroup.getName(),
                    equipmentRepository.count(Objects.requireNonNull(matchGroupId(equipmentGroup.getId()))),
                    countEquipmentCategoryByGroup(equipmentGroup.getId())
            ));
        });
        return list;
    }

    @Override
    public List<StatisticDashboardDto.CountByDepartmentAndStatus> getStatisticByDepartmentAndStatus() {
        List<StatisticDashboardDto.CountByDepartmentAndStatus> list = new ArrayList<>();
        List<Department> allDepartments = departmentRepository.findAll();
        allDepartments.forEach(department -> {
            list.add(new StatisticDashboardDto.CountByDepartmentAndStatus(
                    department.getId(),
                    department.getName(),
                    equipmentRepository.count(Objects.requireNonNull(matchDepartmentId(department.getId()))),
                    countEquipmentWithStatusesByDepartment(department.getId())
            ));
        });
        return list;
    }

    public List<StatisticDashboardDto.CountByCategory> countEquipmentCategoryByGroup(Long groupId) {
        List<EquipmentCategory> equipmentCategoryList = equipmentCategoryRepository.findAllByGroupId(groupId);
        List<StatisticDashboardDto.CountByCategory> countByEquipmentCategories = new ArrayList<>();
        equipmentCategoryList.forEach((equipmentCategory -> {
            countByEquipmentCategories.add(new StatisticDashboardDto.CountByCategory(
                    equipmentCategory.getId(),
                    equipmentCategory.getName(),
                    equipmentRepository.count(matchCategoryId(equipmentCategory.getId()))
            ));
        }));
        return countByEquipmentCategories;
    }

    public Map<EquipmentStatus, Long> countEquipmentWithStatusesByDepartment(Long departmentId) {
        Map<EquipmentStatus, Long> equipmentStatuses = new HashMap<>();
        Arrays.stream(EquipmentStatus.values())
                .forEach(equipmentStatus -> {
                    //do not statistic equipment that has these statuses
                    if (statusNotIn(equipmentStatus)) {
                        return;
                    }
                    //count equipment by status
                    equipmentStatuses.put(
                            equipmentStatus,
                            equipmentRepository.count(matchStatus(equipmentStatus).and(matchDepartmentId(departmentId)))
                    );
                });
        return equipmentStatuses;
    }
}
