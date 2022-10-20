package gym.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exercises_routines")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoutineExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "routine_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore()
    private Routine routine;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", nullable = false, referencedColumnName = "id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Exercise exercise;

    @NotNull
    @Min(1)
    @Max(7)
    private int day;

    private int sets;
    private int reps;
}
