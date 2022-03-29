package gym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name="exercises")
@Getter
@Setter
public class Exercise {
    @Id
    public Long id;

    @Column(nullable = false)
    public String name;

    @ManyToMany()
    @JoinTable(name="exercises_muscle_groups")
    public Set<MuscleGroup> muscleGroups;
}
