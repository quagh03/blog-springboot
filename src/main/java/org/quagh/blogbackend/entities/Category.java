package org.quagh.blogbackend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 75, nullable = false)
    private String title;

    @Column(name = "meta_title", length = 100)
    private String metaTitle;

    @Column(length = 100, nullable = false)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "number_of_posts", nullable = false)
    private Integer numberOfPosts;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonManagedReference
    private Category parentCategory;
}
