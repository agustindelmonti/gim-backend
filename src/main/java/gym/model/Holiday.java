package gym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "description", nullable = false)
    private String description;

    public Holiday() {
    }

    public Holiday(String name, LocalDate date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

}
