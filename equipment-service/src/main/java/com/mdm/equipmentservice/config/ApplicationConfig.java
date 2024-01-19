package com.mdm.equipmentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableAsync
//@EnableMongoRepositories(basePackages = "com.mdm.equipmentservice.model.mongo")  -- commit by PVH
//@EnableJpaRepositories(basePackages = "com.mdm.equipmentservice.model.repository")
public class ApplicationConfig {

}
