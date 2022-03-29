package gym.controllers;

import gym.model.Exercise;
import gym.services.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/exercises")
@AllArgsConstructor
public class ExerciseController {
    private ExerciseService exerciseService;

    @GetMapping
    public List<Exercise> findAll() {
        return exerciseService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Exercise create(@RequestBody Exercise exercise) {
        return exerciseService.create(exercise);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Exercise update(@PathVariable("id") Long id, @RequestBody Exercise exercise) {
        return exerciseService.update(id, exercise);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        exerciseService.delete(id);
    }
}
