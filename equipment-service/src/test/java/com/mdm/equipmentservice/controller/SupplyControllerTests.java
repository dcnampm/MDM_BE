//package com.mdm.equipmentservice.controller;
//
//import com.mdm.equipmentservice.model.dto.SupplyDto;
//import com.mdm.equipmentservice.service.SupplyService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = SupplyController.class)
//@AutoConfigureMockMvc(addFilters = false)
//public class SupplyControllerTests {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private SupplyService supplyService;
//
//    @MockBean
//    private PagedResourcesAssembler<SupplyDto> pagedResourcesAssembler;
//
//    @Test
//    void testGetAllSupplies() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/supply/{id}"))
//                .andExpect(status().is(204));
//    }
//}
