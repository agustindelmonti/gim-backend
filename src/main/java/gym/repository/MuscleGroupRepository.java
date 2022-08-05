package gym.repository;

import gym.model.Exercise;
import gym.model.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {
    public List<MuscleGroup> findAllByOrderByIdAsc();
}
