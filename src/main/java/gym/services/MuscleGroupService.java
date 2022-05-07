package gym.services;

import gym.dtos.MuscleGroupDto;
import gym.model.MuscleGroup;
import gym.repository.MuscleGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class MuscleGroupService {
    private MuscleGroupRepository muscleGroupRepository;

    public MuscleGroup getById(Long id) {
        return muscleGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<MuscleGroup> getAll() {
        return muscleGroupRepository.findAll();
    }

    public MuscleGroup create(MuscleGroupDto muscleGroupDto) {
        return muscleGroupRepository.save(muscleGroupDto.toMuscleGroup());
    }

    public MuscleGroup update(Long id, MuscleGroupDto muscleGroupDto) {
        MuscleGroup muscleGroup = this.getById(id);

        muscleGroup.name = muscleGroupDto.name;

        return muscleGroupRepository.save(muscleGroup);
    }

    public void delete(Long id) {
        muscleGroupRepository.deleteById(id);
    }
}
