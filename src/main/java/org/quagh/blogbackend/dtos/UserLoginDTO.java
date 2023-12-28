package org.quagh.blogbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = "Username is required!")
    private String username;

    @NotBlank(message = "Password cannot be blank!")
    @JsonProperty("password_hash")
    private String passwordHash;
}
