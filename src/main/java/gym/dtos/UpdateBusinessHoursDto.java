package gym.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gym.model.BusinessHours;
import lombok.Data;

import java.time.LocalTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateBusinessHoursDto {
    private int day;
    private LocalTime open;
    private LocalTime close;

    public BusinessHours toBusinessHours() {
        return new BusinessHours(day, open, close);
    }
}