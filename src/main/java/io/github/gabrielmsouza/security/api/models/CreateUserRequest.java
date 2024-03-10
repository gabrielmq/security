package io.github.gabrielmsouza.security.api.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateUserRequest(
        @JsonProperty("login") String login,
        @JsonProperty("password") String password,
        @JsonProperty("name") String name,
        @JsonProperty("groupIds") List<String> groupIds
) {
}
