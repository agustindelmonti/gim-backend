package gym.controllers;

import gym.model.Routine;
import gym.services.RoutineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public Routine create() {
        return routineService.create();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        routineService.delete(id);
    }
}
