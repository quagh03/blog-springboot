package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
