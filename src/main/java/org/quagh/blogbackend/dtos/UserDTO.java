package org.quagh.blogbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("first_name")
    @NotBlank(message = "First name not blank")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name not blank")
    private String lastName;

    @NotBlank(message = "Username not blank")
    private String username;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Email not blank")
    @Email(message = "Invalid email format")
    private String email;

    @JsonProperty("password_hash")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String passwordHash;

    @JsonProperty("retype_password")
    private String retypePassword;

    private String intro;

    private String profile;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;
}
