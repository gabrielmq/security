package io.github.gabrielmsouza.security.domain.repository;

import io.github.gabrielmsouza.security.domain.entity.User;
import io.github.gabrielmsouza.security.domain.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, String> {
    @Query("""
        select distinct g.name
        from UsersGroups ug
        join ug.group g
        join ug.user u
        where u = :user
    """)
    List<String> findGroupsByUser(User user);
}
