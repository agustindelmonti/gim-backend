package gym.controllers;

import gym.dtos.PaymentCreateDto;
import gym.model.Payment;
import gym.services.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController()
@RequestMapping("/api/payments")
@SecurityRequirement(name = "bearer")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public Payment getPaymentsById(@PathVariable("id") UUID paymentId) {
        return paymentService.findById(paymentId);
    }

    @GetMapping()
    public List<Payment> getPaymentsByUser(@RequestParam("user_id") Long userId) {
        return paymentService.getAllByUser(userId);
    }

    @PostMapping("/pay")
    @ResponseStatus(HttpStatus.CREATED)
    public Payment createPayment(@RequestBody PaymentCreateDto payment) {
        return paymentService.createPayment(payment);
    }
}
