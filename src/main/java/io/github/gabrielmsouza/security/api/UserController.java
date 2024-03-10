package io.github.gabrielmsouza.security.api;

import io.github.gabrielmsouza.security.api.models.CreateUserRequest;
import io.github.gabrielmsouza.security.domain.entity.User;
import io.github.gabrielmsouza.security.domain.repository.UserRepository;
import io.github.gabrielmsouza.security.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = Objects.requireNonNull(userService);
    }

    @PostMapping
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<User> create(@RequestBody final CreateUserRequest request) {
        return ResponseEntity.ok(this.userService.create(request));
    }
}
