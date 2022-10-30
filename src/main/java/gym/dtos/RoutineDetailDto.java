package gym.dtos;

import gym.model.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link Routine} entity
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoutineDetailDto {
    Long id;
    String name;
    String user;
    String creator;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public RoutineDetailDto(Routine r) {
        this.id = r.getId();
        this.name = r.getName();
        this.user = r.getMember().getName();
        this.creator = r.getCreator().getName();
        this.createdAt = r.getCreatedAt();
        this.updatedAt = r.getUpdatedAt();
    }
}