package io.github.gabrielmsouza.security.domain.entity;

import jakarta.persistence.*;

@Entity(name = "UsersGroups")
@Table(name = "users_groups")
public class UserGroup {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public UserGroup() {
    }

    private UserGroup(final User user, final Group group) {
        this.user = user;
        this.group = group;
    }

    public static UserGroup with(final User user, final Group group) {
        return new UserGroup(user, group);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
