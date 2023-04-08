package gym.services;

import gym.dtos.ScheduleCreateDto;
import gym.model.Schedule;
import gym.repository.ScheduleRepository;
import gym.utils.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final LocationService locationService;
    private final ServiceService serviceService;

    public ScheduleService(ScheduleRepository scheduleRepository, LocationService locationService, ServiceService serviceService) {
        this.scheduleRepository = scheduleRepository;
        this.locationService = locationService;
        this.serviceService = serviceService;
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Schedule not found"));
    }

    public Schedule save(ScheduleCreateDto schedule) {
        Schedule newSchedule = new Schedule();
        newSchedule.setLocation(locationService.getLocationById(schedule.getLocationId()));
        newSchedule.setService(serviceService.findById(schedule.getServiceId()));
        newSchedule.setWeekday(schedule.getWeekday() + 1);
        newSchedule.setFrom(schedule.getFrom());
        newSchedule.setTo(schedule.getTo());
        return scheduleRepository.save(newSchedule);
    }

    public Schedule updateSchedule(Long id, Schedule schedule) {
        final Schedule scheduleToUpdate = findById(id);
        scheduleToUpdate.setService(schedule.getService());
        scheduleToUpdate.setFrom(schedule.getFrom());
        scheduleToUpdate.setTo(schedule.getTo());
        scheduleToUpdate.setWeekday(schedule.getWeekday());
        scheduleToUpdate.setLocation(schedule.getLocation());
        return scheduleRepository.save(scheduleToUpdate);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> findByLocationId(Long id) {
        return scheduleRepository.findByLocationId(id);
    }

    public List<Schedule> findByServiceId(Long id) {
        return scheduleRepository.findByServiceId(id);
    }
}
