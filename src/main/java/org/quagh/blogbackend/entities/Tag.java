package org.quagh.blogbackend.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 75, nullable = false)
    private String title;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(length = 100, nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "number_of_posts", nullable = false)
    private Integer numberOfPosts;

    @JsonBackReference
    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;
}
