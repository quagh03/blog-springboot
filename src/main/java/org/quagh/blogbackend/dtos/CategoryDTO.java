package org.quagh.blogbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @JsonProperty("parent_id")
    private Long parentId;

    @NotBlank(message = "Category's title cannot be empty")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @JsonProperty("meta_title")
    @NotBlank(message = "Category's meta title cannot be empty")
    @Size(min = 3, max = 200, message = "Meta title must be between 3 and 200 characters")
    private String metaTitle;

    @Size(min = 3, max = 300, message = "Content must be between 3 and 200 characters")
    private String content;

    private String slug;
}
