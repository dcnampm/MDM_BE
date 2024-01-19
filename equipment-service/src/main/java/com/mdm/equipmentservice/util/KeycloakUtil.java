package com.mdm.equipmentservice.util;

public class KeycloakUtil {
    public static String getPermissionNameFromRoleName(String roleName) {
        return roleName.concat(" permissions");
    }

    public static String getPermissionDescriptionFromRoleName(String roleName) {
        return roleName.concat(" granted permissions");
    }

    public static String getPolicyNameFromRoleName(String roleName) {
        return roleName.concat(" policy");
    }

    public static String getPolicyDescriptionFromRoleName(String roleName) {
        return roleName.concat(" only policy");
    }
}
