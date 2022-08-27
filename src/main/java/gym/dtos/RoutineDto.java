package gym.dtos;

import gym.model.MuscleGroup;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

public class RoutineDto {
    public String name;
    public Collection<RoutineExerciseDto> exercises;
}
