package gym.mail;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
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
@AllArgsConstructor
@Log4j2
public class EmailService {
    private MailProperties props;
    private final SpringTemplateEngine springTemplateEngine;

    private Session getSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", props.getHost());
        prop.put("mail.smtp.port", props.getPort());
        prop.put("mail.smtp.ssl.trust", props.getHost());

        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getUsername(), props.getPassword());
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

            helper.setTo(email.getTo());
            helper.setFrom(email.getFrom() == null ? props.getUsername() : email.getFrom());
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

