package gym.services;

import gym.dtos.RoutineDto;
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
import java.util.stream.Collectors;

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

        Routine r = new Routine();
        r.setCreator(creator);
        r.setMember(user);

        return parseExercisesSet(routineDto, r);
    }

    public Routine update(Long id, RoutineDto routineDto) {
        Routine r = this.getById(id);

        final User user = userRepository.findById(routineDto.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        r.setMember(user);
        r.getRoutineExercises().clear();
        return parseExercisesSet(routineDto, r);
    }

    private Routine parseExercisesSet(RoutineDto routineDto, Routine r) {
        var exercises = routineDto.getExercises().stream().map(re -> {
            Exercise exercise = exerciseService.getById(re.getExerciseId());
            return re.toRoutineExercise(exercise, r);
        }).collect(Collectors.toSet());
        r.getRoutineExercises().addAll(exercises);

        r.setName(routineDto.getName());
        r.setFrom(routineDto.getFrom());
        r.setTo(routineDto.getTo());

        return routineRepository.save(r);
    }

    public void delete(Long id) {
        routineRepository.deleteById(id);
    }

}
