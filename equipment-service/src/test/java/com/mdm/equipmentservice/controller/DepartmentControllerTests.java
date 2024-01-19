//package com.mdm.equipmentservice.controller;
//
//import com.mdm.equipmentservice.model.dto.DepartmentWithRelationshipDto;
//import com.mdm.equipmentservice.service.DepartmentService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@ContextConfiguration
//public class DepartmentControllerTests {
//    @Autowired
//    private WebApplicationContext context;
//    private MockMvc mockMvc;
//
//    @MockBean
//    private DepartmentService departmentService;
//    @MockBean
//    private PagedResourcesAssembler<DepartmentWithRelationshipDto> pagedResourcesAssembler;
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context).apply(springSecurity()).build();
//    }
//
//    @Test
//    public void testGetDepartment() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/departments/{id}", 1L))
//                .andExpect(status().isOk());
//    }
//
//}
