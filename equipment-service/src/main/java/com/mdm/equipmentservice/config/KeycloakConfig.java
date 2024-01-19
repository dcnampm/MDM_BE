//package com.mdm.equipmentservice.config;
//
//import com.mdm.equipmentservice.converter.KeycloakAuthorityConverter;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.keycloak.admin.client.Keycloak;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//
//import java.util.Map;
//
//@Configuration
//@Slf4j
//@Deprecated(forRemoval = true)
//public class KeycloakConfig {
//
//    @Value("${keycloak.server}")
//    private String authServerUrl = "";
//
//    @Value("${keycloak.realm-name}")
//    private String realm = "";
//
//    @Value("${keycloak.client-id}")
//    private String clientId = "";
//
//    @Value("${keycloak.client-secret}")
//    private String clientSecret = "";
//
//    @Value("${keycloak.admin.username}")
//    private String adminUsername = "";
//
//    @Value("${keycloak.admin.password}")
//    private String adminPassword = "";
//
//    @Value("${keycloak.admin.realm-name}")
//    private String adminRealm = "";
//
//    @Value("${keycloak.admin.client-id}")
//    private String adminClientId = "";
//
//    @PostConstruct
//    private void validateConfigurationProperties() {
//        if (StringUtils.isAnyBlank(
//                this.authServerUrl,
//                this.realm,
//                this.clientId,
//                this.clientSecret,
//                this.adminUsername,
//                this.adminPassword,
//                this.adminRealm,
//                this.adminClientId
//        )) {
//            throw new RuntimeException(String.format(
//                    "Configuration properties for keycloak is not valid, please check the config server properties." + "\nauthServerUrl: %s, realm: %s, clientId: %s, clientSecret: %s, adminUsername: %s, adminPassword: %s, adminRealm: %s, adminClientId: %s",
//                    StringUtils.defaultIfBlank(this.authServerUrl, "NOT FOUND"),
//                    StringUtils.defaultIfBlank(this.realm, "NOT FOUND"),
//                    StringUtils.defaultIfBlank(this.clientId, "NOT FOUND"),
//                    StringUtils.defaultIfBlank(this.clientSecret, "NOT FOUND"),
//                    StringUtils.defaultIfBlank(this.adminUsername, "NOT FOUND"),
//                    StringUtils.defaultIfBlank(this.adminPassword, "NOT FOUND"),
//                    StringUtils.defaultIfBlank(this.adminRealm, "NOT FOUND"),
//                    StringUtils.defaultIfBlank(this.adminClientId, "NOT FOUND")
//
//            ));
//        }
//    }
//
//    @Bean(name = "keycloakConfiguration")
//    org.keycloak.authorization.client.Configuration keycloakConfiguration() {
//        return new org.keycloak.authorization.client.Configuration(this.authServerUrl, this.realm, this.clientId, Map.of("secret", clientSecret), null);
//    }
//
//    @Bean(name = "keycloakInstance")
//    Keycloak getKeycloakInstance() {
//        return Keycloak.getInstance(this.authServerUrl, this.adminRealm, this.adminUsername, this.adminPassword, this.adminClientId);
//    }
//
//    @Bean
//    JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(keycloakAuthorityConverter(keycloakConfiguration()));
//        return jwtAuthenticationConverter;
//    }
//
//    @Bean
//    KeycloakAuthorityConverter keycloakAuthorityConverter(org.keycloak.authorization.client.Configuration keycloakConfiguration) {
//        return new KeycloakAuthorityConverter(keycloakConfiguration);
//    }
//
//}
