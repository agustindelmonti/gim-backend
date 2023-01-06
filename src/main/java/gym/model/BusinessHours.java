package gym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class BusinessHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "day", nullable = false)
    private int day;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    public BusinessHours() {
    }

    public BusinessHours(int day, LocalTime open, LocalTime close) {
        this.day = day;
        this.openTime = open;
        this.closeTime = close;
    }
}

