package org.coathangerstudios.backend.model.payload;

import java.util.Set;

public record JwtResponse(String token, String username, Set<String> roles) {
    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
