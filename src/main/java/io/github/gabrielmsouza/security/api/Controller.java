package io.github.gabrielmsouza.security.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping(value = "public")
    public ResponseEntity<String> publicRoute() {
        return ResponseEntity.ok("public route ok");
    }

    @GetMapping(value = "/private")
    public ResponseEntity<String> privateRoute(final Authentication authentication) {
        return ResponseEntity.ok("private route ok %s".formatted(authentication.getName()));
    }

    @GetMapping(value = "/admin")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<String> adminRoute() {
        return ResponseEntity.ok("private route ok");
    }
}
