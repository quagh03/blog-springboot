package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
}
