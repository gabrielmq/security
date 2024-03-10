package io.github.gabrielmsouza.security.configuration.filter;

import io.github.gabrielmsouza.security.domain.security.UserAuthentication;
import io.github.gabrielmsouza.security.domain.security.UserIdentifier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class CustomFilter extends OncePerRequestFilter {

    private static final String X_SECRET_HEADER = "x-secret";
    private static final String X_SECRET_HEADER_VALUE = "secr3t";

    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filter
    ) throws ServletException, IOException {
        final var secretHeader = request.getHeader(X_SECRET_HEADER);
        if (Objects.nonNull(secretHeader) && X_SECRET_HEADER_VALUE.equalsIgnoreCase(secretHeader)) {
            final var userIdentifier = new UserIdentifier("id-secret", "Secret", X_SECRET_HEADER, List.of("USER"));
            final var securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(UserAuthentication.with(userIdentifier));
        }
        filter.doFilter(request, response);
    }
}
