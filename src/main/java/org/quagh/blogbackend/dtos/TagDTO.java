package org.quagh.blogbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    @NotBlank(message = "Title not blank")
    @Size(min = 3, max = 25, message = "Title must be at least 3 characters long and maximum 25")
    private String title;

    @NotBlank(message = "Meta title not blank")
    @JsonProperty("meta_title")
    @Size(min = 3, max = 25, message = "Meta title must be at least 3 characters long and maximum 25")
    private String metaTitle;

    @NotBlank(message = "Slug not blank")
    @Size(min = 3, max = 25, message = "Slug must be at least 3 characters long and maximum 25")
    private String slug;

    @NotBlank(message = "Content not blank")
    @Size(min = 3, max = 25, message = "Content must be at least 3 characters long and maximum 25")
    private String content;
}
