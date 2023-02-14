package gym.controllers;


import gym.model.Holiday;
import gym.services.HolidayService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/holidays")
@SecurityRequirement(name = "bearer")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping
    public List<Holiday> getHolidays() {
        return holidayService.getHolidays();
    }

    @GetMapping("/{id}")
    public Holiday getById(@PathVariable("id") Long id) throws Exception {
        return holidayService.getHolidayById(id);
    }

    @PostMapping()
    public Holiday createHoliday(@RequestBody Holiday holiday) {
        return holidayService.save(holiday);
    }

    @PutMapping(value = "/{id}")
    public Holiday updateHoliday(@PathVariable("id") Long id, @RequestBody Holiday holiday) {
        return holidayService.updateHoliday(id, holiday);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteHoliday(@PathVariable("id") Long id) {
        holidayService.deleteHoliday(id);
    }
}
