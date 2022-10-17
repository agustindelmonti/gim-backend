package gym.services;

import gym.dtos.RoutineDto;
import gym.dtos.RoutineExerciseDto;
import gym.model.Exercise;
import gym.model.Routine;
import gym.model.User;
import gym.repository.RoutineRepository;
import gym.repository.UserRepository;
import gym.utils.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoutineService {
    private RoutineRepository routineRepository;
    private UserRepository userRepository;
    private ExerciseService exerciseService;

    public Routine getById(Long id) {
        return routineRepository.findById(id).orElseThrow(() -> new NotFoundException("Routine not found"));
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

    public Routine create(RoutineDto routineDto, User creator) {
        final User user = userRepository.findById(routineDto.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));

        Routine r = routineDto.toRoutine();
        r.setCreator(creator);
        r.setMember(user);

        for (RoutineExerciseDto e : routineDto.getExercises()) {
            Exercise o = exerciseService.getById(e.getExerciseId());
            r.getRoutineExercises().add(e.toRoutineExercise(o));
        }

        return routineRepository.save(r);
    }

    public Routine update(Long id, RoutineDto routineDto) {
        Routine routine = this.getById(id);

        Routine r = routineDto.toRoutine();

        for (RoutineExerciseDto e : routineDto.getExercises()) {
            Exercise o = exerciseService.getById(e.getExerciseId());
            r.getRoutineExercises().add(e.toRoutineExercise(o));
        }

        routine.setName(r.getName());
        routine.getRoutineExercises().clear();
        routine.getRoutineExercises().addAll(r.getRoutineExercises());
        return routineRepository.save(routine);
    }

    public void delete(Long id) {
        routineRepository.deleteById(id);
    }

}
