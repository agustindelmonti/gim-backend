package gym.controllers;

import gym.dtos.RoutineDto;
import gym.model.Routine;
import gym.services.RoutineService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/routines")
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class RoutineController {
    private RoutineService routineService;

    @GetMapping("/{id}")
    public Routine getById(@PathVariable("id") Long id) {
        return routineService.getById(id);
    }

    @GetMapping
    public List<Routine> getRoutines() {
        return routineService.getRoutines();
    }

    @GetMapping(params = "member")
    public Page<Routine> findAllAssignedRoutinesMember(
            @RequestParam(name = "member") Long memberId,
            Pageable pageable) {
        return routineService.findAllRoutinesAssignedMember(memberId, pageable);
    }

    @GetMapping(params = "user")
    public Page<Routine> findAllCreatedRoutinesByUser(
            @RequestParam(name = "user") Long userId,
            Pageable pageable) {
        return routineService.findAllRoutinesCreatedByUser(userId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Routine create(@RequestBody @Valid RoutineDto routineDto) {
        return routineService.create(routineDto);
    }


    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Routine update(@PathVariable("id") Long id, @RequestBody @Valid RoutineDto routineDto) {
        return routineService.update(id, routineDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        routineService.delete(id);
    }
}
