package gym.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class Email {
    private String from;
    private String to;
    private String subject;
    private String template;
    private String attachment;
    private Map<String, Object> properties;
}
