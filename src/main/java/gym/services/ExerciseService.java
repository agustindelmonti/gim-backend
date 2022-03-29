package gym.services;

import gym.model.Exercise;
import gym.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseService {
    ExerciseRepository exerciseRepository;

    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    public Exercise getById(Long id) {
        return exerciseRepository.getById(id);
    }

    public Exercise create(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Exercise update(Long id, Exercise exercise) {
        return exercise;
    }

    public void delete(Long id) {
        exerciseRepository.deleteById(id);
    }
}
