package org.quagh.blogbackend.responses;

import lombok.*;
import org.quagh.blogbackend.entities.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String metaTitle;
    private String slug;
    private String summary;
    private boolean published;
    private Date publishedAt;
    private String thumbnail;
    private Integer views;
    private String content;
    private User author;
    private Category category;
    private List<PostComment> comments;
    private Set<Tag> tags;
    public static PostResponse fromPost(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .metaTitle(post.getMetaTitle())
                .slug(post.getSlug())
                .summary(post.getSummary())
                .published(post.isPublished())
                .publishedAt(post.getPublishedAt())
                .thumbnail(post.getThumbnail())
                .views(post.getViews())
                .content(post.getContent())
                .author(post.getAuthor())
                .category(post.getCategory())
                .comments(post.getComments())
                .tags(post.getTags())
                .build();
    }
}
