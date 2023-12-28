package org.quagh.blogbackend.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "social_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String provider;

    @Column(name = "provider_id", length = 20, nullable = false)
    private String providerId;

    @Column(length = 150, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}
