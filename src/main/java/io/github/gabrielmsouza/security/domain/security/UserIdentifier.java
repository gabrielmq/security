package io.github.gabrielmsouza.security.domain.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserIdentifier {
    private String id;
    private String name;
    private String login;
    private List<String> groups;

    public UserIdentifier() {
        this.groups = new ArrayList<>();
    }

    public UserIdentifier(String id, String name, String login, List<String> groups) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.groups = Objects.nonNull(groups) ? new ArrayList<>(groups) : new ArrayList<>();
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
}
