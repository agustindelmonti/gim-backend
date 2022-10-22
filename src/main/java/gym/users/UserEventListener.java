package gym.users;

import gym.mail.Email;
import gym.mail.EmailService;
import gym.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@AllArgsConstructor
public class UserEventListener {
    private final EmailService emailService;

    @Value("${application.frontend.url}")
    private String url;

    @Async
    @EventListener
    public void handleSuccessfulUserRegistration(SuccessfulUserRegistrationEvent event) {
        log.debug("New user registered -> {}", event.getUser().getId());
        emailService.send(buildSuccessfulRegistrationEmail(event.getUser()));
    }

    private Email buildSuccessfulRegistrationEmail(User user) {
        Email email = new Email();
        email.setTo(user.getEmail());
        email.setSubject("Bienvenido al gimnasio");
        email.setTemplate("registration.html");

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.getName());
        properties.put("url", url);
        email.setProperties(properties);

        return email;
    }
}
