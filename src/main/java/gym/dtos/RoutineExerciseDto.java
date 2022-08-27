package gym.dtos;

import gym.model.Exercise;
import gym.model.RoutineExercise;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class RoutineExerciseDto {
    public Long exerciseId;

    @Min(1)
    @Max(7)
    public int day;

    @Min(1)
    public int sets;

    @Min(1)
    public int reps;
}
