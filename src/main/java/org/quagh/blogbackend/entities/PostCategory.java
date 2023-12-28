package org.quagh.blogbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "post_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PostCategory.PostCategoryId.class)
@Builder
public class PostCategory {
    @Id
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Id
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostCategoryId implements Serializable {
        private Long postId;
        private Long categoryId;
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PostCategoryId that)) return false;
            return Objects.equals(postId, that.postId) &&
                    Objects.equals(categoryId, that.categoryId);
        }
        @Override
        public int hashCode() {
            return Objects.hash(postId, categoryId);
        }
    }
}
