package gym.services;

import gym.model.MuscleGroup;
import gym.repository.MuscleGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MuscleGroupService {
    private MuscleGroupRepository muscleGroupRepository;

    public MuscleGroup getById(Long id) {
        return muscleGroupRepository.findById(id).orElseThrow();
    }

    public List<MuscleGroup> getMuscleGroups() {
        return muscleGroupRepository.findAll();
    }

    public MuscleGroup create(MuscleGroup muscleGroup) {
        return muscleGroupRepository.save(muscleGroup);
    }

    public MuscleGroup update(Long id, MuscleGroup muscleGroup) {
        MuscleGroup existingMuscleGroup = this.getById(id);

        existingMuscleGroup.name = muscleGroup.name;

        return muscleGroupRepository.save(existingMuscleGroup);
    }

    public void delete(Long id) {
        muscleGroupRepository.deleteById(id);
    }
}
