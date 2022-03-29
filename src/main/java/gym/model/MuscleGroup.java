package gym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="muscle_groups")
@Getter
@Setter
public class MuscleGroup {
    @Id
    public Long id;

    @Column(nullable = false)
    public String name;


}
