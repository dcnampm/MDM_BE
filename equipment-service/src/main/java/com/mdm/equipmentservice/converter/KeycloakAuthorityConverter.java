//package com.mdm.equipmentservice.converter;
//
//import lombok.NonNull;
//import lombok.extern.slf4j.Slf4j;
//import org.keycloak.authorization.client.AuthzClient;
//import org.keycloak.authorization.client.Configuration;
//import org.keycloak.authorization.client.representation.TokenIntrospectionResponse;
//import org.keycloak.representations.idm.authorization.AuthorizationRequest;
//import org.keycloak.representations.idm.authorization.AuthorizationResponse;
//import org.keycloak.representations.idm.authorization.Permission;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Deprecated(forRemoval = true)
//public class KeycloakAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
//
//    private final Configuration configuration;
//
//    public KeycloakAuthorityConverter(Configuration keyCloakConfiguration) {
//        this.configuration = keyCloakConfiguration;
//    }
//
//    @Override
//    public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
//        String accessToken = jwt.getTokenValue();
//        //TODO: cache the authority granted by keycloak server. The cache should be invalidated when the user's permission is changed.
//        AuthzClient authzClient = AuthzClient.create(this.configuration);
////        List<String> resourceNames = getAllResourceName(authzClient);
////        List<Permission> permissions = getCurrentUserGrantedPermissionsWithResourceNames(accessToken, authzClient, resourceNames);
//        List<Permission> permissions = getCurrentUserGrantedPermissionsWithResourceNames(accessToken, authzClient, null);
//        Collection<GrantedAuthority> authorities = new HashSet<>();
//        addAuthoritiesFromCurrentUserGrantedPermission(authorities, permissions);
//        addRoleToGrantedAuthoritiesFromJwt(authorities, jwt);
//        log.info("Permissions granted by the keycloak server: {}", authorities);
//        return authorities;
//    }
//
//    /**
//     * Retrieves the permissions granted to the currently authenticated user for the specified resources, using the provided access token and AuthzClient.
//     *
//     * @param accessToken   The access token for the current user.
//     * @param authzClient   The AuthzClient to use for authorization.
//     * @param resourceNames The names of the resources to retrieve permissions for.
//     * @return A List of Permission objects representing the granted permissions for the specified resources.
//     */
//    private List<Permission> getCurrentUserGrantedPermissionsWithResourceNames(String accessToken, AuthzClient authzClient, List<String> resourceNames) {
//        AuthorizationRequest request = new AuthorizationRequest();
////        addResourcesToAuthorizationRequest(request, resourceNames);
//        AuthorizationResponse response = authzClient.authorization(accessToken)
//                .authorize(request); //401 error: must create at least one resource in keycloak, assign scope to the resource, create policy, scope based permission
//        String requestPartyToken = response.getToken();
//        TokenIntrospectionResponse requestingPartyToken = authzClient.protection().introspectRequestingPartyToken(requestPartyToken);
//        return requestingPartyToken.getPermissions();
//    }
//
//    /**
//     * Adds ROLE-based granted authorities to the provided collection of authorities, based on the roles present in the provided JWT.
//     *
//     * @param authorities The collection of GrantedAuthority objects to add to.
//     * @param jwt         The JWT containing the roles to add as authorities.
//     */
//    private void addRoleToGrantedAuthoritiesFromJwt(Collection<GrantedAuthority> authorities, Jwt jwt) {
//        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
//        if (realmAccess == null || realmAccess.isEmpty()) {
//            return;
//        }
//        authorities.addAll(((List<String>) realmAccess.get("roles")).stream()
//                .map(roleName -> "ROLE_" + roleName.toUpperCase())
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toSet()));
//    }
//
//    /**
//     * Adds authorities based on the scopes granted to the current user from the provided list of permissions.
//     *
//     * @param authorities The collection of GrantedAuthority objects to add to.
//     * @param permissions The list of Permission objects containing the granted scopes to add as authorities.
//     */
//    private void addAuthoritiesFromCurrentUserGrantedPermission(
//            Collection<GrantedAuthority> authorities, List<Permission> permissions
//    ) {
//        permissions.forEach(grantedPermission -> authorities.addAll(grantedPermission.getScopes()
//                .stream()
//                .map(scope -> new SimpleGrantedAuthority(scope.toUpperCase()))
//                .collect(Collectors.toSet())));
//    }
//
//    /**
//     * Retrieves the names of all resources from Keycloak.
//     *
//     * @return A List of Strings containing the names of all resources. Return type example: ["equipment", "department",...]
//     */
//    private List<String> getAllResourceName(AuthzClient authzClient) {
//        return Arrays.stream(authzClient.protection().resource().findAll())
//                .toList()
//                .stream()
//                .map(resourceId -> authzClient.protection().resource().findById(resourceId).getName())
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Adds the specified list of resource names to the provided AuthorizationRequest object to receive the permissions granted by Keycloak for the currently
//     * authenticated user..
//     *
//     * @param request       The AuthorizationRequest object to add the resources to.
//     * @param resourceNames The list of resource names to add to the AuthorizationRequest.
//     */
//    private void addResourcesToAuthorizationRequest(AuthorizationRequest request, List<String> resourceNames) {
//        if (resourceNames.isEmpty()) {
//            log.error("Keycloak return empty list of resource.");
//            return;
//        }
//        resourceNames.forEach(request::addPermission);
//    }
//
//}
