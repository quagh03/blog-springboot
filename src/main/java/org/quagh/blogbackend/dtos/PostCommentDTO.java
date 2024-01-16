package org.quagh.blogbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentDTO {
    private String title;
    @NotBlank(message = "Comment's content cannot be empty")
    private String content;
    private Long postId;
    private Long userId;
    private Long parentId;
}
