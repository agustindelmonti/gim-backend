package gym.dtos;

import gym.model.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A DTO for the {@link gym.model.Schedule} entity
 */
@Getter
@Setter
public class ScheduleDto implements Serializable {
    private Long id;
    private Long serviceId;
    private String title;
    private Long locationId;
    private int weekday;
    private LocalTime from;
    private LocalTime to;

    private LocalDateTime start;
    private LocalDateTime end;

    public ScheduleDto() {
    }

    public ScheduleDto(Schedule s) {
        this.id = s.getId();
        this.serviceId = s.getService().getId();
        this.title = s.getService().getName();
        this.locationId = s.getLocation().getId();
        this.weekday = s.getWeekday();
        this.from = s.getFrom();
        this.to = s.getTo();


        // This is a hack to make the calendar work
        // Get the day of the week this week based on the weekday
        // Then add the difference between the day of the week this week and the day of the week in the schedule
        // This will give us the date of the next occurrence of the day of the week in the schedule
        // Then we can set the time to the time in the schedule
        // This will give us the start and end times for the event
        LocalDateTime now = LocalDateTime.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        int diff = weekday - dayOfWeek;
        this.start = now.plusDays(diff).toLocalDate().atTime(from);
        this.end = now.plusDays(diff).toLocalDate().atTime(to);
    }
}