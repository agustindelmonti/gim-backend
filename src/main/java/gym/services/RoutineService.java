package gym.services;

import gym.dtos.RoutineDto;
import gym.dtos.RoutineExerciseDto;
import gym.model.Routine;
import gym.model.RoutineExercise;
import gym.repository.RoutineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class RoutineService {
    private RoutineRepository routineRepository;
    private ExerciseService exerciseService;

    public List<Routine> getRoutines() {
        return routineRepository.findAll();
    }

    public Routine getById(Long id) {
        return routineRepository.findById(id).orElseThrow();
    }

    public Routine create(RoutineDto routineDto) {
        Routine routine = new Routine();

        routine.name = routineDto.name;
        routine.routineExercises = new HashSet<RoutineExercise>();

        for (RoutineExerciseDto routineExerciseDto: routineDto.exercises) {
            RoutineExercise routineExercise = new RoutineExercise();
            routineExercise.day = routineExerciseDto.day;
            routineExercise.sets = routineExerciseDto.sets;
            routineExercise.reps = routineExerciseDto.reps;
            routineExercise.routine = routine;
            routineExercise.exercise = exerciseService.getById(routineExerciseDto.exerciseId);

            routine.routineExercises.add(routineExercise);
        }

        return routineRepository.save(routine);
    }

    public Routine update(Long id, RoutineDto routineDto) {
        Routine routine = this.getById(id);

        routine.name = routineDto.name;
        routine.getRoutineExercises().clear();

        for (RoutineExerciseDto routineExerciseDto: routineDto.exercises) {
            RoutineExercise routineExercise = new RoutineExercise();
            routineExercise.day = routineExerciseDto.day;
            routineExercise.sets = routineExerciseDto.sets;
            routineExercise.reps = routineExerciseDto.reps;
            routineExercise.routine = routine;
            routineExercise.exercise = exerciseService.getById(routineExerciseDto.exerciseId);

            routine.routineExercises.add(routineExercise);
        }


        return routineRepository.save(routine);
    }

    public void delete(Long id) {
        routineRepository.deleteById(id);
    }
}
