package org.quagh.blogbackend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "phone_number", length = 15, nullable = false, unique = true)
    private String phoneNumber;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", length = 80, nullable = false)
    private String passwordHash;

    @CreationTimestamp
    @Column(name = "registered_at")
    private Date registeredAt;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(columnDefinition = "TINYTEXT")
    private String intro;

    @Column(columnDefinition = "TEXT")
    private String profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "facebook_account_id")
    private Integer facebookAccountId;

    @Column(name = "google_account_id")
    private Integer googleAccountId;

}

