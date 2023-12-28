package org.quagh.blogbackend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 75, nullable = false)
    private String title;

    @Column(name = "meta_title", length = 100)
    private String metaTitle;

    @Column(length = 100, nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TINYTEXT")
    private String summary;

    @Column(nullable = false)
    private boolean published;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "published_at")
    private Date publishedAt;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    @Column(nullable = false)
    private Integer views;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User author;

    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostCategory> categories = new ArrayList<>();
}
