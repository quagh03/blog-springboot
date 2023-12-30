package org.quagh.blogbackend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.quagh.blogbackend.entities.Tag;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("category")
    private Tag tag;
}
