package gym.controllers;

import gym.dtos.MuscleGroupCreateDto;
import gym.model.MuscleGroup;
import gym.services.MuscleGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/muscle-groups")
@AllArgsConstructor
public class MuscleGroupController {
    private MuscleGroupService muscleGroupService;

    @GetMapping
    public List<MuscleGroup> getMuscleGroups() {
        return muscleGroupService.getMuscleGroups();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MuscleGroup create(@RequestBody @Valid MuscleGroupCreateDto muscleGroupCreateDto) {
        return muscleGroupService.create(muscleGroupCreateDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MuscleGroup update(@PathVariable("id") Long id, @RequestBody @Valid MuscleGroupCreateDto muscleGroupCreateDto) {
        return muscleGroupService.update(id, muscleGroupCreateDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        muscleGroupService.delete(id);
    }
}
