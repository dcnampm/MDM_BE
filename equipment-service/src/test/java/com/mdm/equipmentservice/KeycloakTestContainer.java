//package com.mdm.equipmentservice;
//
//import dasniko.testcontainers.keycloak.KeycloakContainer;
//import io.restassured.RestAssured;
//import jakarta.annotation.PostConstruct;
//import net.bytebuddy.utility.dispatcher.JavaDispatcher;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public abstract class KeycloakTestContainer {
//    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakTestContainer.class.getName());
//    @LocalServerPort
//    private int port;
//    static final KeycloakContainer keycloak;
//
//    static {
//        keycloak = new KeycloakContainer().withRealmImportFile("/keycloak/mdm-local-realm.json");
//        keycloak.start();
//    }
//    @PostConstruct
//    public void init() {
//        RestAssured.baseURI = "http://localhost:" + port;
//    }
//    @DynamicPropertySource
//    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
//        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloak.getAuthServerUrl() + "realms/mdm-local");
//
//    }
//    protected String getBearerToken() {
//
//        try (Keycloak keycloakAdminClient = KeycloakBuilder.builder()
//                .serverUrl(keycloak.getAuthServerUrl())
//                .realm("mdm-local")
//                .clientId("api-gateway")
//                .clientSecret("0SfQyERviyX6Mal4QuTzT3ZOF7W939Wj")
//                .username("admin")
//                .password("123456")
//                .build()) {
//
//            String access_token = keycloakAdminClient.tokenManager().getAccessToken().getToken();
//
//            System.out.println("Access:" + access_token);
//
//            return "Bearer " + access_token;
//        } catch (Exception e) {
//            LOGGER.error("Can't obtain an access token from Keycloak!", e);
//        }
//        return null;
//    }
//}
