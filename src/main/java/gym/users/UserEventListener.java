package gym.users;

import gym.mail.Email;
import gym.mail.EmailService;
import gym.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class UserEventListener {
    private final EmailService emailService;

    @Value("${application.frontend.url}")
    private String url;

    public UserEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void handleSuccessfulUserRegistration(SuccessfulUserRegistrationEvent event) {
        log.debug("New user registered -> {}", event.getUser().getId());
        emailService.send(buildSuccessfulRegistrationEmail(event.getUser()));
    }

    private Email buildSuccessfulRegistrationEmail(User user) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.getName());
        properties.put("url", url);

        return Email.builder()
                .to(user.getEmail())
                .subject("Bienvenido al gim!")
                .template("registration.html")
                .properties(properties)
                .build();
    }
}
