package io.github.gabrielmsouza.security.api;

import io.github.gabrielmsouza.security.domain.entity.Group;
import io.github.gabrielmsouza.security.domain.repository.GroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "groups")
public class GroupController {
    private final GroupRepository groupRepository;

    public GroupController(final GroupRepository groupRepository) {
        this.groupRepository = Objects.requireNonNull(groupRepository);
    }

    @PostMapping
    @Transactional
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Group> create(@RequestBody final Group group) {
        return ResponseEntity.ok(this.groupRepository.save(group));
    }

    @GetMapping
    @Transactional(readOnly = true)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<List<Group>> findAll() {
        return ResponseEntity.ok(this.groupRepository.findAll());
    }
}
