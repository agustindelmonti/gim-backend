package gym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="muscle_groups")
@Getter @Setter
public class MuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true)
    public String name;

    @ManyToMany()
    @JoinTable(
            name="exercises_muscle_groups",
            joinColumns = @JoinColumn(name = "muscle_group_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    @JsonIgnore
    public Set<Exercise> exercises;
}
