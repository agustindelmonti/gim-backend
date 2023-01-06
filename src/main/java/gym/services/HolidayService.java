package gym.services;

import gym.model.Holiday;
import gym.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public Holiday getHolidayById(Long id) {
        return holidayRepository.getById(id);
    }

    public List<Holiday> getHolidays() {
        return holidayRepository.findAll();
    }
    
    public Holiday save(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    public Holiday updateHoliday(Long id, Holiday holiday) {
        Holiday holidayToUpdate = holidayRepository.getById(id);
        holidayToUpdate.setDate(holiday.getDate());
        holidayToUpdate.setDescription(holiday.getDescription());
        return holidayRepository.save(holidayToUpdate);
    }

    public void deleteHoliday(Long id) {
        holidayRepository.deleteById(id);
    }
}
