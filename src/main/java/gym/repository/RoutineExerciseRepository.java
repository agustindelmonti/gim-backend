package gym.repository;

import gym.model.RoutineExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineExerciseRepository extends JpaRepository<RoutineExercise, Long> {
    List<RoutineExercise> getByRoutineId(Long routineId);
    RoutineExercise getByRoutineIdAndExerciseId(Long routineId, Long exerciseId);
}
