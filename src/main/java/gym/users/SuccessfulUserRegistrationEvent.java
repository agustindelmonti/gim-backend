package gym.users;

import gym.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SuccessfulUserRegistrationEvent extends ApplicationEvent {
    private User user;

    public SuccessfulUserRegistrationEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}

