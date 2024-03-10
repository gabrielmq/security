package io.github.gabrielmsouza.security.domain.service;

import io.github.gabrielmsouza.security.api.models.CreateUserRequest;
import io.github.gabrielmsouza.security.domain.entity.User;
import io.github.gabrielmsouza.security.domain.entity.UserGroup;
import io.github.gabrielmsouza.security.domain.repository.GroupRepository;
import io.github.gabrielmsouza.security.domain.repository.UserGroupRepository;
import io.github.gabrielmsouza.security.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            final UserRepository userRepository,
            final GroupRepository groupRepository,
            final UserGroupRepository userGroupRepository,
            final PasswordEncoder passwordEncoder
    ) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.groupRepository = Objects.requireNonNull(groupRepository);
        this.userGroupRepository = Objects.requireNonNull(userGroupRepository);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    @Transactional
    public User create(final CreateUserRequest request) {
        final var user = new User();
        user.setName(request.name());
        user.setLogin(request.login());
        user.setPassword(this.passwordEncoder.encode(request.password()));
        this.userRepository.save(user);
        for (String groupId : request.groupIds()) {
            this.groupRepository
                .findById(groupId)
                .map(group -> UserGroup.with(user, group))
                .ifPresent(userGroupRepository::save);
        }
        return user;
    }

    public User findUserWithGroups(final String login) {
        return this.userRepository.findByLogin(login)
                .map(user -> {
                    user.setGroups(this.userGroupRepository.findGroupsByUser(user));
                    return user;
                })
                .orElse(null);

    }
}
