package gym.dtos;

import gym.model.Routine;
import gym.model.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

/**
 * A DTO for the {@link Routine} entity
 */

@Getter
@Setter
public class RoutineWithExercisesDto implements Serializable {
    private Long id;
    private String name;
    private Collection<RoutineExerciseDto> routineExercises;
    private UserDto member;
    private UserDto creator;
    private LocalDate from;
    private LocalDate to;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoutineWithExercisesDto(Routine r) {
        this.id = r.getId();
        this.name = r.getName();
        this.routineExercises =  r.getRoutineExercises().stream().map(RoutineExerciseDto::new).toList();
        this.member = new UserDto(r.getMember());
        this.creator = new UserDto(r.getCreator());
        this.from = r.getFrom();
        this.to = r.getTo();
        this.createdAt = r.getCreatedAt();
        this.updatedAt = r.getUpdatedAt();
    }

    /**
     * A DTO for the {@link User} entity
     */
    @Getter
    @Setter
    public static class UserDto implements Serializable {
        private Long id;
        private String name;
        private String email;

        public UserDto(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
        }
    }
}