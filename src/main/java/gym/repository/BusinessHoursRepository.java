package gym.repository;

import gym.model.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {
    void deleteAllByLocationId(Long locationId);

    void deleteAllByLocationIdAndDay(Long locationId, String day);

    List<BusinessHours> findByLocationIdAndDay(Long locationId, int day);
}