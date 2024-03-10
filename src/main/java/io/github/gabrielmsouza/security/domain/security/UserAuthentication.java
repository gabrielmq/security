package io.github.gabrielmsouza.security.domain.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Objects;

public record UserAuthentication(UserIdentifier userIdentifier) implements Authentication {
    public UserAuthentication {
        Objects.requireNonNull(userIdentifier, "'userIdentifier' should not be null");
    }

    public static UserAuthentication with(final UserIdentifier userIdentifier) {
        return new UserAuthentication(userIdentifier);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userIdentifier.getGroups().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userIdentifier;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}

    @Override
    public String getName() {
        return this.userIdentifier.getName();
    }
}
