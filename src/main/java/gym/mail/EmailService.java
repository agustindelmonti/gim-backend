package gym.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService {
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private String port;

    private final SpringTemplateEngine springTemplateEngine;

    private Session getSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);

        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void send(Email email) {
        Session session = getSession();
        try {
            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariables(email.getProperties());
            String htmlBody = springTemplateEngine.process(email.getTemplate(), context);

            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            helper.setFrom(email.getFrom());
            helper.setSubject(email.getSubject());
            helper.setText(htmlBody, true);

            if (email.getAttachment() != null) {
                FileSystemResource file = new FileSystemResource(new File(email.getAttachment()));
                helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            }

            Transport.send(message);
            log.info("Email sent successfully");
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
        }
    }
}

