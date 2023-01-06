package gym.services;

import gym.model.BusinessHours;
import gym.model.Holiday;
import gym.model.Location;
import gym.repository.BusinessHoursRepository;
import gym.repository.LocationRepository;
import gym.utils.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Represents a day on the calendar with a specific set of opening and closing times
@Getter
@Setter
class CalendarDay {
    private LocalDate date;
    private LocalTime openTime;
    private LocalTime closeTime;

    public CalendarDay(LocalDate date, LocalTime openTime, LocalTime closeTime) {
        this.date = date;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public CalendarDay() {

    }
}


@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final HolidayService holidayService;
    private BusinessHoursRepository businessHoursRepository;

    public LocationService(LocationRepository locationRepository, HolidayService holidayService, BusinessHoursRepository businessHoursRepository) {
        this.locationRepository = locationRepository;
        this.holidayService = holidayService;
        this.businessHoursRepository = businessHoursRepository;
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new NotFoundException("Location not found"));
    }

    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(Long id, Location location) {
        Location locationToUpdate = getLocationById(id);
        locationToUpdate.setName(location.getName());
        locationToUpdate.setAddress(location.getAddress());
        locationToUpdate.setPhone(location.getPhone());
        return locationRepository.save(locationToUpdate);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public void deleteAllLocationsByIdInBatch(Iterable<Long> ids) {
        locationRepository.deleteAllByIdInBatch(ids);
    }

    public void setBusinessHoursInBatch(Long id, Set<BusinessHours> businessHours) {
        Location location = getLocationById(id);
        location.setBusinessHours(businessHours);
        locationRepository.saveAndFlush(location);
    }

    // Returns a list of calendar days for the given location and date range
    public List<CalendarDay> getCalendar(Location location, LocalDate startDate, LocalDate endDate) {
        if (endDate.isAfter(startDate.plusMonths(1))) {
            throw new IllegalArgumentException("Date range must be less than a month");
        }

        List<CalendarDay> calendar = new ArrayList<>();

        // Get all holidays in date range
        Map<LocalDate, Holiday> holidayCalendar = holidayService.getHolidayCalendarByDateRange(startDate, endDate);

        // Iterate through each day in the date range
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {

            // Check if this date is a holiday
            if (holidayCalendar.containsKey(date)) {
                // Set hours for holiday
                Holiday h = holidayCalendar.get(date);
                calendar.add(new CalendarDay(date, h.getOpenTime(), h.getCloseTime()));
            } else {
                // Set regular business hours
                int dayOfWeek = date.getDayOfWeek().getValue(); // 1 is Monday, 7 is Sunday
                List<BusinessHours> regularHours = businessHoursRepository.findByLocationIdAndDay(location.getId(), dayOfWeek);
                LocalDate finalDate = date;
                calendar.addAll(regularHours.stream().map(bh -> new CalendarDay(finalDate, bh.getOpenTime(), bh.getCloseTime())).toList());
            }
        }
        return calendar;
    }
}
