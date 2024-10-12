package dev.seano.quokka.feature.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    @JsonProperty("user") USER,
    @JsonProperty("admin") ADMIN;

    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority("ROLE_" + name());
    }
}
