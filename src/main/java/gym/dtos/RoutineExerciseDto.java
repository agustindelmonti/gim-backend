package gym.dtos;

import gym.model.Exercise;
import gym.model.RoutineExercise;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
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

    public RoutineExercise toRoutineExercise(Exercise exercise) {
        RoutineExercise ex = new RoutineExercise();
        ex.setDay(day);
        ex.setSets(sets);
        ex.setReps(reps);
        ex.setExercise(exercise);
        return ex;
    }
}
