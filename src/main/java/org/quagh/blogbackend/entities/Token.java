package org.quagh.blogbackend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    private String token;

    @Column(name = "token_type", length = 10, nullable = false)
    private String tokenType;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(nullable = false)
    private boolean revoked;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}
