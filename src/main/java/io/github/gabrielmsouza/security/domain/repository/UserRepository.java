package io.github.gabrielmsouza.security.domain.repository;

import io.github.gabrielmsouza.security.domain.entity.Group;
import io.github.gabrielmsouza.security.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByLogin(String login);
}
