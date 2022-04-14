package gym.controllers;

import gym.dtos.MuscleGroupDto;
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
    public List<MuscleGroup> getAll() {
        return muscleGroupService.getAll();
    }

    @GetMapping("/{id}")
    public MuscleGroup getOne(@PathVariable("id") Long id) {
        return muscleGroupService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MuscleGroup create(@RequestBody @Valid MuscleGroupDto muscleGroupDto) {
        return muscleGroupService.create(muscleGroupDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MuscleGroup update(@PathVariable("id") Long id, @RequestBody @Valid MuscleGroupDto muscleGroupDto) {
        return muscleGroupService.update(id, muscleGroupDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        muscleGroupService.delete(id);
    }
}
