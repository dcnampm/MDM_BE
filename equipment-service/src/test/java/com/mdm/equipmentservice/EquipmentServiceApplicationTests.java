package com.mdm.equipmentservice;
import com.mdm.equipmentservice.service.EquipmentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@WithMockUser()
class EquipmentServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
