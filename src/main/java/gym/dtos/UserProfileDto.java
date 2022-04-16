package gym.dtos;

import gym.model.Role;
import gym.model.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class UserProfileDto implements Serializable {
    private final Long id;
    private final String email;
    private final String nroDoc;
    private final Collection<RoleDto> roles;

    public UserProfileDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nroDoc = user.getNroDoc();
        this.roles = user.getRoles().stream().map(RoleDto::new).collect(Collectors.toList());
    }

    @Data
    public static class RoleDto implements Serializable {
        private final Long id;
        private final String name;

        public RoleDto(Role role) {
            this.id = role.getId();
            this.name = role.getName();
        }
    }
}
