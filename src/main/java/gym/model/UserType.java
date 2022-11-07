package gym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum UserType {
    MEMBER("member"),
    STAFF("staff");

    private final String type;

}
