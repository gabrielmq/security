package io.github.gabrielmsouza.security.configuration.providers;

import io.github.gabrielmsouza.security.domain.security.UserAuthentication;
import io.github.gabrielmsouza.security.domain.security.UserIdentifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MasterPasswordAuthenticationProvider implements AuthenticationProvider {
    private static final String MASTER_LOGIN = "master";
    private static final String MASTER_PASSWORD = "12";

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final var login = authentication.getName();
        final var password = (String) authentication.getCredentials();
        if (MASTER_LOGIN.equalsIgnoreCase(login) && MASTER_PASSWORD.equalsIgnoreCase(password)) {
            final var userIdentifier = new UserIdentifier("MASTER", "Master", MASTER_LOGIN, List.of("ADMIN"));
            return UserAuthentication.with(userIdentifier);
        }
        return null;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }
}
