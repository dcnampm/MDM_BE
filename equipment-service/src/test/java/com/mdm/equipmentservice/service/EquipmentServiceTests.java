//package com.mdm.equipmentservice.service;
//
//import com.mdm.commonservice.exception.ResourceNotFoundException;
//import com.mdm.commonservice.exception.ValidationFailedException;
//import com.mdm.commonservice.util.MessageUtil;
//import com.mdm.commonservice.util.UniqueValidationUtil;
//import com.mdm.commonservice.util.ValidationUtil;
//import com.mdm.equipmentservice.mapper.EquipmentCategoryMapper;
//import com.mdm.equipmentservice.mapper.EquipmentMapper;
//import com.mdm.equipmentservice.mapper.EquipmentMapperImpl;
//import com.mdm.equipmentservice.model.dto.EquipmentWithRelationshipDto;
//import com.mdm.equipmentservice.model.dto.UpsertEquipmentDto;
//import com.mdm.equipmentservice.model.entity.Department;
//import com.mdm.equipmentservice.model.entity.Equipment;
//import com.mdm.equipmentservice.model.entity.User;
//import com.mdm.equipmentservice.model.repository.EquipmentRepository;
//import com.mdm.equipmentservice.model.repository.FileStorageRepository;
//import com.mdm.equipmentservice.model.repository.UserRepository;
//import com.mdm.equipmentservice.query.param.GetEquipmentsQueryParam;
//import com.mdm.equipmentservice.query.predicate.EquipmentPredicate;
//import com.mdm.equipmentservice.service.impl.EquipmentServiceImpl;
//import com.mdm.equipmentservice.util.SecurityUtil;
//import org.checkerframework.checker.units.qual.A;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mapstruct.factory.Mappers;
//import org.mockito.*;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.test.context.ContextConfiguration;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Predicate;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class EquipmentServiceTests {
//    @Mock
//    private MessageUtil messageUtil;
//    @Mock
//    private EquipmentRepository equipmentRepository;
//    @Mock
//    private EquipmentMapper equipmentMapper;
//    @Mock
//    private ValidationUtil validationUtil;
//    @Mock
//    private UniqueValidationUtil uniqueValidationUtil;
//    @Mock
//    private UserRepository userRepository;
//    private FileStorageService fileStorageService;
//    private FileStorageRepository fileStorageRepository;
//    private Equipment equipment = new Equipment();
//    private List<Equipment> equipmentList = new ArrayList<>();
//    private User user = new User();
//    @InjectMocks
//    private EquipmentServiceImpl equipmentService;
//    @BeforeEach
//    public void setUp() {
//        Long id = 1L;
//        equipment.setId(id);
//        equipment.setName("test");
//    }
//
//    @Test
//    public void givenIdNotExist_whenGetEquipmentById_thenThrowException() {
//        //given
//        given(equipmentRepository.findById(any())).willReturn(Optional.empty());
//        //when
//        when(messageUtil.getMessage("equipmentNotFound")).thenReturn("Equipment not found");
//        assertThatThrownBy(() -> equipmentService.getEquipmentById(any()))
//                .isInstanceOf(ResourceNotFoundException.class)
//                .hasMessage("Equipment not found");
//    }
//
//
//
//    //if user has EQUIPMENT.READ_ALL permission or ROLE_ADMIN, return the equipment
//    @Test
//    public void givenAuthoredUser_whenGetEquipmentById_thenReturnDto() {
//        try (MockedStatic<SecurityUtil> securityUtil = Mockito.mockStatic(SecurityUtil.class)) {
//        //given
//            when(equipmentRepository.findById(any())).thenReturn(Optional.of(equipment));;
//            securityUtil.when(
//                    () -> SecurityUtil.currentUserHasAnyAuthorities("EQUIPMENT.READ_ALL", "ROLE_ADMIN"))
//                    .thenReturn(true);
//            EquipmentWithRelationshipDto dto = new EquipmentWithRelationshipDto();
//            when(equipmentMapper.toEquipmentWithRelationshipDto(equipment)).thenReturn(dto);
//        // when
//            EquipmentWithRelationshipDto equipmentDto = equipmentService.getEquipmentById(any());
//        // then
//            assertThat(equipmentDto).isNotNull();
//            }
//        }
//
//
//    //if the equipment does not belong to any department, it means that anyone who does not have EQUIPMENT.READ_ALL or ROLE_ADMIN authority cannot access it
//    @Test
//    public void givenEquipmentWithNullDepartment_whenGetEquipmentById_thenThrowException() {
//        try (MockedStatic<SecurityUtil> securityUtil = Mockito.mockStatic(SecurityUtil.class)) {
//        // given
//            equipment.setDepartment(null);
//            given(equipmentRepository.findById(any())).willReturn(Optional.of(equipment));
//            securityUtil.when(
//                    () -> SecurityUtil.currentUserHasAnyAuthorities("EQUIPMENT.READ_ALL", "ROLE_ADMIN"))
//                    .thenReturn(false);
//            when(messageUtil.getMessage("equipmentAccessDenied")).thenReturn("equipmentAccessDenied");
//        // then
//            assertThatThrownBy(
//                    () -> equipmentService.getEquipmentById(any()))
//                    .isInstanceOf(AccessDeniedException.class)
//                    .hasMessage("equipmentAccessDenied");
//        }
//    }
//
//    //if the user does not belong to any department, it means that this user cannot access any equipment
//    @Test
//    public void givenUserWithNullDepartment_whenGetEquipmentById_thenThrowException() {
//
//        try (MockedStatic<SecurityUtil> securityUtil = Mockito.mockStatic(SecurityUtil.class)) {
//            //given
//            given(equipmentRepository.findById(any())).willReturn(Optional.of(equipment));
//            user.setDepartment(null);
//            securityUtil.when(() -> SecurityUtil.getCurrentLoggedInUser(userRepository)).thenReturn(user);
//            when(messageUtil.getMessage("equipmentAccessDenied")).thenReturn("equipmentAccessDenied");
//            //then
//            assertThatThrownBy(
//                    () -> equipmentService.getEquipmentById(any()))
//                    .isInstanceOf(AccessDeniedException.class)
//                    .hasMessage("equipmentAccessDenied");
//        }
//    }
//
//    @Test
//    public void givenUserAndEquipmentDoNotHaveTheSameDepartmentId_whenGetEquipmentById_thenThrowException() {
//
//        try (MockedStatic<SecurityUtil> securityUtil = Mockito.mockStatic(SecurityUtil.class)) {
//            //given
//            given(equipmentRepository.findById(any())).willReturn(Optional.of(equipment));
//            Department departmentEq = new Department();
//            departmentEq.setId(1L);
//            equipment.setDepartment(departmentEq);
//
//            Department departmentUser = new Department();
//            departmentUser.setId(2L);
//            user.setDepartment(departmentUser);
//
//            securityUtil.when(() -> SecurityUtil.getCurrentLoggedInUser(userRepository)).thenReturn(user);
//            when(messageUtil.getMessage("equipmentAccessDenied")).thenReturn("equipmentAccessDenied");
//            //then
//            assertThatThrownBy(
//                    () -> equipmentService.getEquipmentById(any()))
//                    .isInstanceOf(AccessDeniedException.class)
//                    .hasMessage("equipmentAccessDenied");
//        }
//    }
//
//}
