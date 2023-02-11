package gym.dtos;

import gym.model.PackageUser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link gym.model.PackageUser} entity
 */
@Getter
@Setter
public class PackageUserDto implements Serializable {
    private Long id;
    private Long packageId;
    private String packageName;
    private Boolean packageActive;
    private LocalDateTime paymentPaymentDate;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;

    public PackageUserDto(PackageUser packageUser) {
        this.id = packageUser.getId();
        this.packageId = packageUser.getPack().getId();
        this.packageName = packageUser.getPack().getName();
        this.packageActive = packageUser.getPack().getActive();

        if (packageUser.getPayment() != null) {
            this.paymentPaymentDate = packageUser.getPayment().getPaymentDate();
        }

        this.createdAt = packageUser.getCreatedAt();
        this.startDate = packageUser.getStartDate();
        this.endDate = packageUser.getEndDate();
        this.status = packageUser.getStatus().toString();
    }
}