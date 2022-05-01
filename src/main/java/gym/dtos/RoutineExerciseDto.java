package gym.dtos;

import gym.model.RoutineExercise;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class RoutineExerciseDto {
    @Min(1)
    @Max(7)
    public int day;

    @Min(1)
    public int sets;

    @Min(1)
    public int reps;

    public RoutineExercise toRoutineExercise() {
        RoutineExercise routineExercise = new RoutineExercise();

        routineExercise.day = this.day;
        routineExercise.sets = this.sets;
        routineExercise.reps = this.reps;

        return routineExercise;
    }
}
