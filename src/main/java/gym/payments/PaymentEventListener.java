package gym.payments;

import gym.mail.Email;
import gym.mail.EmailService;
import gym.model.Payment;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@AllArgsConstructor
public class PaymentEventListener {
    private final EmailService emailService;

    @Async
    @EventListener
    public void handleSuccessfulPayment(SuccessfulPaymentEvent event) {
        log.debug("Email payment invoice id -> {}", event.getPayment().getId());
        emailService.send(buildSuccessfulPaymentEmail(event.getPayment()));
    }

    private Email buildSuccessfulPaymentEmail(Payment payment) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", payment.getUser().getName());
        properties.put("paymentDatetime", payment.getPaymentDate().toString());
        properties.put("amount", payment.getAmount());

        return Email.builder()
                .to(payment.getUser().getEmail())
                .subject("Payment")
                .template("successful_payment.html")
                .properties(properties)
                .build();
    }
}
