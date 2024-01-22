package org.quagh.blogbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostImageDTO {
    @NotBlank(message = "Image URL not blank")
    @JsonProperty("imageUrl")
    private List<String> imageUrl;
}
