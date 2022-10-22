package gym.payments;

import gym.model.Payment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SuccessfulPaymentEvent extends ApplicationEvent {
    private Payment payment;

    public SuccessfulPaymentEvent(Object source, Payment payment) {
        super(source);
        this.payment = payment;
    }
}

