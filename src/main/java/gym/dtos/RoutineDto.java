package gym.dtos;

import gym.model.MuscleGroup;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

public class RoutineDto {
    @NotBlank
    public String name;
    public Collection<RoutineExerciseDto> exercises;
}
