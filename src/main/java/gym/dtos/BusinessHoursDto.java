package gym.dtos;

import gym.model.BusinessHours;
import lombok.Data;

import java.time.LocalTime;

@Data
public class BusinessHoursDto {
    private Long id;
    private int day;
    private LocalTime open;
    private LocalTime close;

    public BusinessHoursDto(BusinessHours bh) {
        this.id = bh.getId();
        this.day = bh.getDay();
        this.open = bh.getOpenTime();
        this.close = bh.getCloseTime();
    }
}