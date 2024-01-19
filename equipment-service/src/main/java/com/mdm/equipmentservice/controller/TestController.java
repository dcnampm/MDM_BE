//package com.mdm.equipmentservice.controller;
//
////import com.mdm.equipmentservice.config.KeycloakConfig;
//import com.mdm.equipmentservice.keycloak.repository.KeycloakRoleRepository;
//import com.mdm.equipmentservice.keycloak.repository.KeycloakUserRepository;
//import com.mdm.equipmentservice.keycloak.service.KeycloakRoleService;
//import com.mdm.equipmentservice.keycloak.service.KeycloakUserService;
//import com.mdm.equipmentservice.response.GenericResponse;
//import jakarta.ws.rs.core.Response;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.resource.AuthorizationResource;
//import org.keycloak.admin.client.resource.ClientResource;
//import org.keycloak.admin.client.resource.RealmResource;
//import org.keycloak.admin.client.resource.ScopePermissionsResource;
//import org.keycloak.authorization.client.Configuration;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.keycloak.representations.idm.authorization.ScopePermissionRepresentation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/api/v1/departments")
////@Import(KeycloakConfig.class)
//public class TestController {
//    private final ApplicationContext context;
//
//
//    private final Keycloak keycloak;
//
//    private final RealmResource realmResource;
//
//    private final ScopePermissionsResource scopePermissionsResource;
//
//    private final AuthorizationResource authorizationResource;
//
//    private final ClientResource clientResource;
//
//    private final KeycloakRoleRepository keycloakRoleRepository;
//
//    private final KeycloakUserRepository keycloakUserRepository;
//
//    private final KeycloakRoleService keycloakRoleService;
//
//    private final KeycloakUserService keycloakUserService;
//
//    @Autowired
//    public TestController(
//            ApplicationContext context, Keycloak keycloak, KeycloakRoleRepository keycloakRoleRepository,
//            KeycloakUserRepository keycloakUserRepository, KeycloakRoleService keycloakRoleService, KeycloakUserService keycloakUserService
//    ) {
//        this.context = context;
//        this.keycloak = keycloak;
//        String trueClientId = this.keycloak.realm("mdm-local").clients().findByClientId("api-gateway").get(0).getId();
//
//        this.realmResource = this.keycloak.realm("mdm-local");
//        this.clientResource = this.realmResource.clients().get(trueClientId);
//        this.authorizationResource = this.clientResource.authorization();
//        this.scopePermissionsResource = this.authorizationResource.permissions().scope();
//        this.keycloakRoleRepository = keycloakRoleRepository;
//        this.keycloakUserRepository = keycloakUserRepository;
//        this.keycloakRoleService = keycloakRoleService;
//        this.keycloakUserService = keycloakUserService;
//    }
//
//
//    @GetMapping("/test")
//    public String test() {
//        return "test";
//    }
//
//    @GetMapping("/test-permission")
//    public ResponseEntity<GenericResponse<ScopePermissionRepresentation>> testPermission(@RequestParam String permissionName) {
//        ScopePermissionRepresentation scopePermissionRepresentation = this.scopePermissionsResource.findByName(permissionName);
//        return ResponseEntity.ok(new GenericResponse<>(scopePermissionRepresentation));
//    }
//
//
//    @PostMapping("/test-add-user")
//    public void testuser(/*@RequestParam String scope*/) {
//        Configuration configuration = context.getBean("keycloakConfiguration", Configuration.class);
//        Keycloak keycloak = context.getBean("keycloakInstance", Keycloak.class);
//        UserRepresentation user = new UserRepresentation();
//        user.setEnabled(true);
//        user.setUsername("test");
//        user.setFirstName("test");
//        user.setLastName("test");
//        user.setEmail("test@test.test");
//        Response response = keycloak.realm("mdm-local").users().create(user);
//        CredentialRepresentation password = new CredentialRepresentation();
//        password.setType(CredentialRepresentation.PASSWORD);
//        password.setValue("test");
//        password.setTemporary(false);
//    }
//
//}
