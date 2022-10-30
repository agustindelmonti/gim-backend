package gym.dtos;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Data
public class RoutineDto {
    @NotNull
    private Long userId;
    @NotBlank
    private String name;
    @NotNull
    LocalDate from;
    @FutureOrPresent
    LocalDate to;

    private Collection<RoutineExerciseDto> exercises = new HashSet<>();
}
