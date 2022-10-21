package gym.services;

import gym.dtos.PaymentCreateDto;
import gym.model.Payment;
import gym.model.User;
import gym.repository.PaymentRepository;
import gym.utils.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserService userService;

    public Payment findById(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    public List<Payment> getAllByUser(Long userId) {
        final User user = userService.getById(userId);
        return paymentRepository.findAllByUser(user);
    }

    public Payment createPayment(PaymentCreateDto payment) {
        final User user = userService.getById(payment.getUserId());
        //TODO: check if user has already an active payment

        Payment p = new Payment();
        p.setAmount(payment.getAmount());
        p.setUser(user);

        //TODO: send notification or email to user
        return paymentRepository.save(p);
    }
}
