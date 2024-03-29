package gym.controllers;

import gym.dtos.ExerciseDto;
import gym.model.Exercise;
import gym.services.ExerciseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/exercises")
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class ExerciseController {
    private ExerciseService exerciseService;

    @GetMapping
    public List<Exercise> getExercises() {
        return exerciseService.getExercises();
    }

    @GetMapping("/{id}")
    public Exercise getById(@PathVariable("id") Long id) {
        return exerciseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Exercise create(@RequestBody @Valid ExerciseDto exerciseDto) {
        return exerciseService.create(exerciseDto);
    }

    @PutMapping(value = "/{id}")
    public Exercise update(@PathVariable("id") Long id, @RequestBody @Valid ExerciseDto exerciseDto) {
        return exerciseService.update(id, exerciseDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        exerciseService.delete(id);
    }
}
