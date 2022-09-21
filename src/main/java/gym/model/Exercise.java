package gym.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="exercises")
@Getter @Setter
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true)
    public String name;

    @ManyToMany()
    @JoinTable(
        name="exercises_muscle_groups",
        joinColumns = @JoinColumn(name = "exercise_id"),
        inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    public Set<MuscleGroup> muscleGroups;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    public Collection<RoutineExercise> routineExercises;
}
