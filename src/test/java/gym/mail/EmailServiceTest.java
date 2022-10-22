package gym.mail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendHtmlMessageTest() {
        Email email = new Email();
        email.setTo("agus.delmonti1@gmail.com");
        email.setFrom("lahoti.ashish20@gmail.com");
        email.setSubject("Welcome Email from CodingNConcepts");
        email.setTemplate("welcome.html");

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Ashish");
        properties.put("subscriptionDate", LocalDate.now().toString());
        properties.put("technologies", Arrays.asList("Python", "Go", "C#"));
        email.setProperties(properties);

        Assertions.assertDoesNotThrow(() -> emailService.send(email));
    }
}