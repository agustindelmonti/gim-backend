package gym.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class PaymentCreateDto {
    private Long userId;

    @NotNull
    @Positive
    private BigDecimal amount;
}
