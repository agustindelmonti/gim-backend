package gym.services;

import gym.dtos.ExerciseDto;
import gym.model.Exercise;
import gym.model.MuscleGroup;
import gym.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseService {
    private ExerciseRepository exerciseRepository;
    private MuscleGroupService muscleGroupService;

    public List<Exercise> getExercises() {
        return exerciseRepository.findAll();
    }

    public Exercise getById(Long id) {
        return exerciseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Exercise create(ExerciseDto exerciseDto) {
        Exercise exercise = exerciseDto.toExcercise();

        exercise.muscleGroups = new HashSet<>();

        for (Long muscleGroupId: exerciseDto.muscleGroupIds) {
            MuscleGroup muscleGroup = muscleGroupService.getById(muscleGroupId);

            exercise.muscleGroups.add(muscleGroup);
        }

        return exerciseRepository.save(exercise);
    }

    public Exercise update(Long id, ExerciseDto exerciseDto) {
        Exercise exercise = this.getById(id);

        exercise.name = exerciseDto.name;

        exercise.muscleGroups = new HashSet<>();

        for (Long muscleGroupId: exerciseDto.muscleGroupIds) {
            MuscleGroup muscleGroup = muscleGroupService.getById(muscleGroupId);

            exercise.muscleGroups.add(muscleGroup);
        }

        return exerciseRepository.save(exercise);
    }

    public void delete(Long id) {
        exerciseRepository.deleteById(id);
    }
}
