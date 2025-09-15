package org.example.bankprojectibk.Model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoleEnum {
    ROLE_SUPER_ADMIN,
    ROLE_ADMIN,
    ROLE_USER;


    @JsonCreator
    public static RoleEnum from(String v) {
        String s = v.trim().toUpperCase();
        if (!s.startsWith("ROLE_")) s = "ROLE_" + s;
        return RoleEnum.valueOf(s);
    }

}
