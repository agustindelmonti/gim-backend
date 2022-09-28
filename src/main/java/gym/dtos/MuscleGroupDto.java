package gym.dtos;

import gym.model.MuscleGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MuscleGroupDto {
    @NotBlank(message = "Campo requerido")
    public String name;

    public MuscleGroup toMuscleGroup() {
        MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.name = this.name;

        return muscleGroup;
    }
}