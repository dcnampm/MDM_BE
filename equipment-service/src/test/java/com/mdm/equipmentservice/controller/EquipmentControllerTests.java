//package com.mdm.equipmentservice.controller;
//
//import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
//import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockBearerTokenAuthentication;
//import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockJwtAuth;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.ObjectCodec;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mdm.commonservice.config.CommonSecurityConfig;
//import com.mdm.equipmentservice.config.SecurityConfig;
//import com.mdm.equipmentservice.model.dto.EquipmentDto;
//import com.mdm.equipmentservice.model.dto.UpsertEquipmentDto;
//import com.mdm.equipmentservice.service.EquipmentService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.testcontainers.dockerclient.UnixSocketClientProviderStrategy;
//
//import java.io.File;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {SecurityConfig.class})
////@WebMvcTest(EquipmentController.class)
//public class EquipmentControllerTests {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private EquipmentService equipmentService;
//    @MockBean
//    private PagedResourcesAssembler<EquipmentDto> pagedResourcesAssembler;
//
//
//    @Test
//    @WithAnonymousUser
//    void givenRequestIsAnonymous_whenGetEquipments_thenUnauthorized() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/equipments"))
//                .andExpect(status().isUnauthorized());
//    }
//    @Test
//    @WithMockUser(authorities = "EQUIPMENT.CREATE")
//    void givenRequestIsAuthenticated_whenGetEquipments_thenOk() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/equipments"))
//                .andExpect(status().isOk());
//    }
//}
