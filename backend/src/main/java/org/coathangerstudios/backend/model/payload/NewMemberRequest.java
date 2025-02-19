package org.coathangerstudios.backend.model.payload;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.coathangerstudios.backend.security.customValidator.ageLimit.AgeLimit;

import java.time.LocalDate;


@Data
public class NewMemberRequest {
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[\\p{L}0-9_\\-.+]+$", message = "Username can only contain letters, digits, underscores (_), hyphens (-), periods (.), and plus signs (+).")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    @Size(message = "Password must be 8-20 characters long", min = 8, max = 20)
    private String password;
    @Email(message = "Email address must be a valid email format")
    private String email;
    @Past
    @AgeLimit
    private LocalDate birthDate;

    public NewMemberRequest(String username, String password, String email, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
    }

    public NewMemberRequest() {
    }
}
