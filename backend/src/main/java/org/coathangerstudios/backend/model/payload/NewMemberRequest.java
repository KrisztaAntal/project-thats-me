package org.coathangerstudios.backend.model.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class NewMemberRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @NotBlank(message = "Password cannot be blank")
    @Size(message = "Password must be 8-20 characters long", min = 8, max = 20)
    private String password;
    @Email(message = "Email address must be a valid email format")
    private String email;
    @NotBlank
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Birthdate must be in the format yyyy-MM-dd"
    )
    private String birthdate;
}
