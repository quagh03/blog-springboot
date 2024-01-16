package org.quagh.blogbackend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.quagh.blogbackend.entities.PostComment;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCommentResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<String> errors;

    @JsonProperty("category")
    private PostComment postComment;
}
