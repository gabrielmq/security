package io.github.gabrielmsouza.security.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "human-resources")
public class HRController {
    @GetMapping(value = "recruiter")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'HR_MANAGER', 'HR_RECRUITER')")
    public ResponseEntity<String> recruiter() {
       return ResponseEntity.ok("recruiter route ok");
    }

    @GetMapping(value = "manager")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'HR_MANAGER')")
    public ResponseEntity<String> manager() {
        return ResponseEntity.ok("manager route ok");
    }
}
