package gym.services;

import gym.dtos.MuscleGroupCreateDto;
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

    public MuscleGroup create(MuscleGroupCreateDto muscleGroupCreateDto) {
        return muscleGroupRepository.save(muscleGroupCreateDto.toMuscleGroup());
    }

    public MuscleGroup update(Long id, MuscleGroupCreateDto muscleGroupCreateDto) {
        MuscleGroup existingMuscleGroup = this.getById(id);

        existingMuscleGroup.name = muscleGroupCreateDto.name;

        return muscleGroupRepository.save(existingMuscleGroup);
    }

    public void delete(Long id) {
        muscleGroupRepository.deleteById(id);
    }
}
