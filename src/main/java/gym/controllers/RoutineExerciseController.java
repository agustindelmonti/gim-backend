package gym.controllers;

import gym.dtos.RoutineExerciseDto;
import gym.model.RoutineExercise;
import gym.services.RoutineExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/routines/exercises")
@AllArgsConstructor
public class RoutineExerciseController {
    private RoutineExerciseService routineExerciseService;

    @GetMapping
    public List<RoutineExercise> getRoutineExercises() {
        return this.routineExerciseService.getRoutineExercises();
    }
    @GetMapping("/{id}")
    public RoutineExercise getById(@PathVariable("id") Long id) {
        return routineExerciseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoutineExercise create(@RequestBody @Valid RoutineExerciseDto routineExerciseDto) {
        return routineExerciseService.create(routineExerciseDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoutineExercise update(@PathVariable("id") Long id, @RequestBody @Valid RoutineExerciseDto routineExerciseDto) {
        return routineExerciseService.update(id, routineExerciseDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        routineExerciseService.delete(id);
    }
}
