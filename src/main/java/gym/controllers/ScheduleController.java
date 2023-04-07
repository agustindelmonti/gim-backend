package gym.controllers;

import gym.dtos.ScheduleCreateDto;
import gym.dtos.ScheduleDto;
import gym.model.Schedule;
import gym.services.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<ScheduleDto> getSchedules() {
        return scheduleService.findAll().stream().map(ScheduleDto::new).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ScheduleDto getSchedule(@PathVariable Long id) {
        final Schedule schedule = scheduleService.findById(id);
        return new ScheduleDto(schedule);
    }

    @PostMapping
    public Schedule createSchedule(@RequestBody ScheduleCreateDto schedule) {
        return scheduleService.save(schedule);
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable("id") Long id, @RequestBody Schedule schedule) {
        return scheduleService.updateSchedule(id, schedule);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable("id") Long id) {
        scheduleService.deleteSchedule(id);
    }

    @GetMapping("/location/{id}")
    public List<ScheduleDto> getSchedulesByLocationId(@PathVariable("id") Long id) {
        return scheduleService.findByLocationId(id).stream().map(ScheduleDto::new).collect(Collectors.toList());
    }

}
