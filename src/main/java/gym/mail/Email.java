package gym.mail;

import lombok.*;

import java.util.Map;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String from;
    private String to;
    private String subject;
    private String template;
    private String attachment;
    private Map<String, Object> properties;
}
