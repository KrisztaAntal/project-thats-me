package org.coathangerstudios.backend.model.payload;

import lombok.Data;

@Data
public class MemberLoginRequest {
    private String usernameOrEmail;
    private String password;
}
