package org.coathangerstudios.backend.model.payload;

import lombok.Data;

@Data
public class MemberLoginRequest {
    private String usernameOrEmail;
    private String password;

    public MemberLoginRequest(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
}
