package gym.services;

import gym.model.Routine;
import gym.repository.RoutineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class RoutineService {
    private RoutineRepository routineRepository;

    public List<Routine> getRoutines() {
        return routineRepository.findAll();
    }

    public Routine getById(Long id) {
        return routineRepository.findById(id).orElseThrow();
    }

    public Routine create() {
        Routine routine = new Routine();

        return routineRepository.save(routine);
    }

    public void delete(Long id) {
        routineRepository.deleteById(id);
    }
}
