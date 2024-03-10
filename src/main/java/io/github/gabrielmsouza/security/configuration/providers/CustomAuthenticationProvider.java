package io.github.gabrielmsouza.security.configuration.providers;

import io.github.gabrielmsouza.security.domain.security.UserAuthentication;
import io.github.gabrielmsouza.security.domain.security.UserIdentifier;
import io.github.gabrielmsouza.security.domain.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(final UserService userService, final PasswordEncoder passwordEncoder) {
        this.userService = Objects.requireNonNull(userService);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }


    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final var login = authentication.getName();
        final var password = (String) authentication.getCredentials();
        final var user = this.userService.findUserWithGroups(login);
        if (Objects.nonNull(user) && passwordEncoder.matches(password, user.getPassword())) {
            final var userIdentifier = new UserIdentifier(user.getId(), user.getName(), user.getLogin(), user.getGroups());
            return UserAuthentication.with(userIdentifier);
        }
        return null;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
