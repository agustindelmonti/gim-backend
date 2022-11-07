package gym.services;

import gym.dtos.PaymentCreateDto;
import gym.model.Payment;
import gym.model.User;
import gym.repository.PaymentRepository;
import gym.utils.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Payment findById(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    public List<Payment> getAllByUser(Long userId) {
        final User user = userService.getById(userId);
        return paymentRepository.findAllByUser(user);
    }

    public Payment createPayment(PaymentCreateDto paymentCreateDto) {
        final User user = userService.getById(paymentCreateDto.getUserId());
        //TODO: check if user has already an active paymentCreateDto

        Payment payment = new Payment();
        payment.setAmount(paymentCreateDto.getAmount());
        payment.setUser(user);
        return paymentRepository.save(payment);
    }
}
