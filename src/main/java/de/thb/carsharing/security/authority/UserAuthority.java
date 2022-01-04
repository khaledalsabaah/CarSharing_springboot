package de.thb.carsharing.security.authority;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

    public final static String ROLE_CUSTOMER = "ROLE_CUSTOMER";
    public final static String ROLE_ADMIN = "ROLE_ADMIN";

    @Override
    public String getAuthority() {
        return ROLE_CUSTOMER;
    }

}
