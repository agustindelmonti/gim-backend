package gym.services;

import gym.dtos.RoutineExerciseDto;
import gym.model.RoutineExercise;
import gym.repository.RoutineExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoutineExerciseService {
    private RoutineExerciseRepository routineExerciseRepository;
    private RoutineService routineService;
    private ExerciseService exerciseService;

    public List<RoutineExercise> getAllByRoutine(Long routineId) {
        return routineExerciseRepository.getByRoutineId(routineId);
    }

    public RoutineExercise getOne(Long routineId, Long exerciseId) {
        return routineExerciseRepository.getByRoutineIdAndExerciseId(routineId, exerciseId);
    }

    public RoutineExercise create(Long routineId, Long exerciseId, RoutineExerciseDto routineExerciseDto) {
        RoutineExercise routineExercise = routineExerciseDto.toRoutineExercise();

        routineExercise.routine = routineService.getById(routineId);
        routineExercise.exercise = exerciseService.getById(exerciseId);

        return this.routineExerciseRepository.save(routineExercise);
    }

    public RoutineExercise update(Long routineId, Long exerciseId, RoutineExerciseDto routineExerciseDto) {
        RoutineExercise routineExercise = this.getOne(routineId, exerciseId);

        routineExercise.day = routineExerciseDto.day;
        routineExercise.sets = routineExerciseDto.sets;
        routineExercise.reps = routineExerciseDto.reps;

        return routineExerciseRepository.save(routineExercise);
    }

    public void delete(Long id) {
        routineExerciseRepository.deleteById(id);
    }
}
