package com.mdm.equipmentservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CommonSecurityConfig.class})
public class SecurityConfig {
}
