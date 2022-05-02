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

    public List<RoutineExercise> getRoutineExercises() {
        return routineExerciseRepository.findAll();
    }

    public RoutineExercise getById(Long routineExerciseId) {
        return routineExerciseRepository.findById(routineExerciseId).orElseThrow();
    }

    public RoutineExercise create(RoutineExerciseDto routineExerciseDto) {
        RoutineExercise routineExercise = routineExerciseDto.toRoutineExercise();

        routineExercise.routine = routineService.getById(routineExerciseDto.routineId);
        routineExercise.exercise = exerciseService.getById(routineExerciseDto.exerciseId);

        return this.routineExerciseRepository.save(routineExercise);
    }

    public RoutineExercise update(Long routineExerciseId, RoutineExerciseDto routineExerciseDto) {
        RoutineExercise routineExercise = this.getById(routineExerciseId);

        routineExercise.day = routineExerciseDto.day;
        routineExercise.sets = routineExerciseDto.sets;
        routineExercise.reps = routineExerciseDto.reps;

        routineExercise.routine = routineService.getById(routineExerciseDto.routineId);
        routineExercise.exercise = exerciseService.getById(routineExerciseDto.exerciseId);

        return routineExerciseRepository.save(routineExercise);
    }

    public void delete(Long id) {
        routineExerciseRepository.deleteById(id);
    }
}
