package gym.repository;

import gym.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByLocationId(Long id);

    List<Schedule> findByServiceId(Long id);
}