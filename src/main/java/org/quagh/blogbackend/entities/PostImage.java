package org.quagh.blogbackend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "post_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Post post;
}
