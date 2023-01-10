package gym.services;

import gym.model.BusinessHours;
import gym.repository.BusinessHoursRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BusinessHoursService {
    private final BusinessHoursRepository businessHoursRepository;

    public BusinessHoursService(BusinessHoursRepository businessHoursRepository) {
        this.businessHoursRepository = businessHoursRepository;
    }

    public List<BusinessHours> findAll() {
        return businessHoursRepository.findAll();
    }

    public List<BusinessHours> findByLocationId(Long id) {
        return businessHoursRepository.findByLocationId(id);
    }
    
    public List<BusinessHours> findByLocationIdAndDay(Long locationId, int day) {
        return businessHoursRepository.findByLocationIdAndDay(locationId, day);
    }

    public BusinessHours save(BusinessHours businessHours) {
        return businessHoursRepository.save(businessHours);
    }

    public void deleteAll() {
        businessHoursRepository.deleteAll();
    }

    public void deleteAllByLocationId(Long locationId) {
        businessHoursRepository.deleteAllByLocationId(locationId);
    }

    public void deleteAllByLocationIdAndDay(Long locationId, String day) {
        businessHoursRepository.deleteAllByLocationIdAndDay(locationId, day);
    }
}
