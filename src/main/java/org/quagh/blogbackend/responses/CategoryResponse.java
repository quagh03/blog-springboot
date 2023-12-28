package org.quagh.blogbackend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.quagh.blogbackend.entities.Category;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("category")
    private Category category;
}
