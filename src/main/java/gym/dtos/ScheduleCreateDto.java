package gym.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import gym.model.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * A DTO for the {@link Schedule} entity
 */
@Getter
@Setter
public class ScheduleCreateDto implements Serializable {
    private Long serviceId;
    private Long locationId;
    @JsonProperty("dayOfWeek")
    private int weekday;
    private LocalTime from;
    private LocalTime to;
    private boolean allDay;
}