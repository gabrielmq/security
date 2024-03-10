package io.github.gabrielmsouza.security.domain.repository;

import io.github.gabrielmsouza.security.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
}
