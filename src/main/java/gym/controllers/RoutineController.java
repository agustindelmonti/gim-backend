package gym.controllers;

import gym.dtos.ExerciseDto;
import gym.dtos.RoutineDto;
import gym.model.Exercise;
import gym.model.Routine;
import gym.services.RoutineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/routines")
@AllArgsConstructor
public class RoutineController {
    private RoutineService routineService;

    @GetMapping
    public List<Routine> getRoutines() {
        return routineService.getRoutines();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Routine create(@RequestBody @Valid RoutineDto routineDto) {
        return routineService.create(routineDto);
    }

    @GetMapping("/{id}")
    public Routine getById(@PathVariable("id") Long id) {
        return routineService.getById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Routine update(@PathVariable("id") Long id, @RequestBody @Valid RoutineDto routineDto) {
        return routineService.update(id, routineDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        routineService.delete(id);
    }
}
