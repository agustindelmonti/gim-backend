package gym.dtos;

import gym.model.Payment;
import gym.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Data
public class MemberAccessStatusDto {
    private Long id;
    private String name;
    private String nroDoc;
    private Date birthday;
    private LocalDateTime lastPaymentDate;
    private LocalDateTime expirationDate;
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private boolean accountNonLocked;

    public MemberAccessStatusDto(User u) {
        this.id = u.getId();
        this.name = u.getName();
        this.nroDoc = u.getNroDoc();
        this.birthday = u.getBirthday();

        final Optional<Payment> lastPayment = u.getPayments().stream().findFirst();
        if (lastPayment.isPresent()) {
            final Payment p = lastPayment.get();
            this.lastPaymentDate = p.getPaymentDate();
            this.expirationDate = p.getExpirationDate();
        }

        this.enabled = u.isEnabled();
        this.credentialsNonExpired = u.isCredentialsNonExpired();
        this.accountNonExpired = u.isAccountNonExpired();
        this.accountNonLocked = u.isAccountNonLocked();
    }
}
