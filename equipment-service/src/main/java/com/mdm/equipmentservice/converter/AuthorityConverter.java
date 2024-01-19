//package com.mdm.equipmentservice.converter;
//
//import com.mdm.equipmentservice.model.entity.Role;
//import com.mdm.equipmentservice.model.repository.UserRepository;
//import jakarta.validation.constraints.NotNull;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
//public class AuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
//
//    private final UserRepository userRepository;
//
//    public AuthorityConverter(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Collection<GrantedAuthority> convert(@NotNull Jwt jwt) {
//        String username = (String) jwt.getClaims().get("preferred_username");
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        userRepository.findByUsernameIgnoreCase(username).ifPresent(user -> {
//            Role role = user.getRole();
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//            if (role.getPermissions() == null || role.getPermissions().isEmpty()) {
//                return;
//            }
//            role.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
//        });
//        return authorities;
//    }
//}
