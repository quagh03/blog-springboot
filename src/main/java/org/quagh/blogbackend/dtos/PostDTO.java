package org.quagh.blogbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    @NotNull(message = "AuthorId is required")
    @JsonProperty("author_id")
    private Long authorId;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @JsonProperty("meta_title")
    @NotBlank(message = "Meta title is required")
    @Size(min = 3, max = 200, message = "Meta title must be between 3 and 200 characters")
    private String metaTitle;

    @NotBlank(message = "Summary is required")
    @Size(min = 3, max = 200, message = "Summary must be between 3 and 200 characters")
    private String summary;

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Slug is required")
    @Size(min = 3, max = 200, message = "Summary must be between 3 and 200 characters")
    private String slug;

    private String thumbnail;

    @NotNull(message = "CategoryId is required")
    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("tag_ids")
    private List<Long> tagIds;
}
