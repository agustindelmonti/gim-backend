package gym.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "package_users")
@Getter
@Setter
public class PackageUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "package_id", nullable = false)
    private Package pack;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Transient
    private PackageStatus status;

    @Formula("(CASE WHEN end_date IS NULL THEN -1 ELSE DATEDIFF(end_date, start_date) END)")
    private int daysLeft;

    @PostLoad
    public void postLoad() {
        if (this.endDate == null) {
            this.status = PackageStatus.ACTIVE;
        } else if (this.endDate.isAfter(LocalDateTime.now())) {
            this.status = PackageStatus.EXPIRED;
        } else {
            this.status = PackageStatus.INACTIVE;
        }
    }
}