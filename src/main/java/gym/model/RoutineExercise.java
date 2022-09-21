package gym.model;

import com.fasterxml.jackson.annotation.*;
import gym.model.Routine;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="exercises_routines")
@Getter @Setter
public class RoutineExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne()
    @JoinColumn(name = "routine_id")
    @JsonProperty("routine_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    public Routine routine;

    @ManyToOne()
    @JoinColumn(name = "exercise_id")
    @JsonProperty("exercise_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    public Exercise exercise;

    @NotNull
    @Min(1)
    @Max(7)
    public int day;

    public int sets;
    public int reps;
}
