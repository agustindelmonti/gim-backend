package gym.dtos;

import gym.model.Exercise;
import gym.model.Routine;
import gym.model.RoutineExercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineExerciseDto {
    @NotNull
    public Long exerciseId;

    @Min(1)
    @Max(7)
    private int day;

    @Min(1)
    private int sets;

    @Min(1)
    private int reps;

    public RoutineExerciseDto(RoutineExercise r) {
        this.exerciseId = r.getExercise().getId();
        this.day = r.getDay();
        this.sets = r.getSets();
        this.reps = r.getReps();
    }

    public RoutineExercise toRoutineExercise(Exercise exercise, Routine routine) {
        RoutineExercise ex = new RoutineExercise();
        ex.setDay(day);
        ex.setSets(sets);
        ex.setReps(reps);
        ex.setExercise(exercise);
        ex.setRoutine(routine);
        return ex;
    }
}
