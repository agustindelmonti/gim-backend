package gym.controllers;

import gym.dtos.RoutineExerciseDto;
import gym.model.RoutineExercise;
import gym.services.RoutineExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/routines")
public class RoutineExerciseController {
    @Autowired
    RoutineExerciseService routineExerciseService;

    @GetMapping("{routineId}/exercises")
    public List<RoutineExercise> getExercises(@PathVariable Long routineId) {
        return this.routineExerciseService.getAllByRoutine(routineId);
    }

    @PostMapping("{routineId}/exercises/{exerciseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public RoutineExercise create(@PathVariable Long routineId, @PathVariable Long exerciseId, @RequestBody @Valid RoutineExerciseDto routineExerciseDto) {
        return routineExerciseService.create(routineId, exerciseId, routineExerciseDto);
    }

    @PutMapping("{routineId}/exercises/{exerciseId}")
    @ResponseStatus(HttpStatus.OK)
    public RoutineExercise update(@PathVariable Long routineId, @PathVariable Long exerciseId, RoutineExerciseDto routineExerciseDto) {
        return routineExerciseService.update(routineId, exerciseId, routineExerciseDto);
    }

    @DeleteMapping(value = "/exercises/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        routineExerciseService.delete(id);
    }
}
