package gym.services;

import gym.dtos.RoutineDto;
import gym.dtos.RoutineExerciseDto;
import gym.model.Routine;
import gym.model.RoutineExercise;
import gym.model.User;
import gym.repository.RoutineRepository;
import gym.repository.UserRepository;
import gym.utils.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class RoutineService {
    private RoutineRepository routineRepository;
    private UserRepository userRepository;
    private ExerciseService exerciseService;

    public Routine getById(Long id) {
        return routineRepository.findById(id).orElseThrow();
    }

    public List<Routine> getRoutines() {
        return routineRepository.findAll();
    }

    public Page<Routine> findAllRoutinesAssignedMember(Long userId, Pageable pageable) {
        final User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return routineRepository.findByMember(user, pageable);
    }

    public Page<Routine> findAllRoutinesCreatedByUser(Long userId, Pageable pageable) {
        final User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return routineRepository.findByCreator(user, pageable);
    }

    public Routine create(RoutineDto routineDto) {
        Routine routine = new Routine();

        routine.name = routineDto.name;
        routine.routineExercises = new HashSet<>();

        for (RoutineExerciseDto routineExerciseDto : routineDto.exercises) {
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
