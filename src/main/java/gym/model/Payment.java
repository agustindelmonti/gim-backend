package gym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {
    static final int VALID_MEMBERSHIP_DAYS = 30;

    @Id
    @Column(length = 16)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @Column
    @Positive
    private BigDecimal amount;

    @Column(name = "payment_date")
    @CreationTimestamp
    private LocalDateTime paymentDate;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public LocalDateTime getExpirationDate() {
        return paymentDate.plusDays(VALID_MEMBERSHIP_DAYS);
    }

}