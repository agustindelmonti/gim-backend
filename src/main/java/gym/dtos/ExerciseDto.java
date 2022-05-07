package gym.dtos;

import gym.model.Exercise;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;

@Data
public class ExerciseDto {
    @NotBlank
    public String name;

    public HashSet<Long> muscleGroupIds = new HashSet<>();

    public Exercise toExcercise() {
        Exercise exercise = new Exercise();
        exercise.name = this.name;

        return exercise;
    }
}
