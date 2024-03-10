package io.github.gabrielmsouza.security.configuration;

import io.github.gabrielmsouza.security.configuration.filter.CustomFilter;
import io.github.gabrielmsouza.security.configuration.providers.CustomAuthenticationProvider;
import io.github.gabrielmsouza.security.configuration.providers.MasterPasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    private static final String[] PUBLIC_PATHS = {"/public", "/h2/**"};

    @Bean
    SecurityFilterChain securityFilterChain(
            final HttpSecurity httpSecurity,
            final MasterPasswordAuthenticationProvider authenticationProvider,
            final CustomAuthenticationProvider customAuthenticationProvider,
            final CustomFilter customFilter
    ) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                            .requestMatchers(PUBLIC_PATHS).permitAll()
                            .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .authenticationProvider(customAuthenticationProvider)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .build();
    }

    @Bean
    UserDetailsService inMemoryDetailsManager(final PasswordEncoder passwordEncoder) {
        final var commonUser = User.builder()
                .username("user")
                .password(passwordEncoder.encode("123"))
                .roles("USER")
                .build();

        final var adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("123"))
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(commonUser, adminUser);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // Customizando o prefixo para roles, substituindo o padrão ROLE_
    GrantedAuthorityDefaults grantedAuthority() {
        return new GrantedAuthorityDefaults("");
    }
}
