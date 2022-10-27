package gym.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum UserType {
    MEMBER("member"),
    STAFF("staff");

    private final String type;

    UserType(String type) {
        this.type = type;
    }
}
