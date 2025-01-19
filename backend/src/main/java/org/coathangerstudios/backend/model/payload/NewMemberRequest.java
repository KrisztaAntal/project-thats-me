package org.coathangerstudios.backend.model.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.coathangerstudios.backend.security.customValidator.ageLimit.AgeLimit;

import java.time.LocalDate;


@Data
public class NewMemberRequest {
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[\\p{L}0-9_\\-.+]+$", message = "Username can only contain letters, digits, underscores (_), hyphens (-), periods (.), and plus signs (+).")
    private String username;
    @NotBlank(message = "Firstname cannot be blank")
    @Pattern(regexp = "^[\\p{L}0-9 _\\-.+]+$", message = "Firstname can only contain letters, digits, spaces, underscores (_), hyphens (-), periods (.), and plus signs (+).")
    private String firstName;
    @NotBlank(message = "Lastname cannot be blank")
    @Pattern(regexp = "^[\\p{L}0-9 _\\-.+]+$", message = "Lastname can only contain letters, digits, spaces, underscores (_), hyphens (-), periods (.), and plus signs (+).")
    private String lastName;
    @NotBlank(message = "Password cannot be blank")
    @Size(message = "Password must be 8-20 characters long", min = 8, max = 20)
    private String password;
    @Email(message = "Email address must be a valid email format")
    private String email;
    @AgeLimit
    private LocalDate birthdate;
}
