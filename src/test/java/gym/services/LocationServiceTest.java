package gym.services;

import gym.model.BusinessHours;
import gym.model.Holiday;
import gym.model.Location;
import gym.repository.BusinessHoursRepository;
import gym.repository.HolidayRepository;
import gym.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LocationServiceTest {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private BusinessHoursRepository businessHoursRepository;
    @Autowired
    private HolidayRepository holidayRepository;
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        HolidayService holidayService = new HolidayService(holidayRepository);
        locationService = new LocationService(locationRepository, holidayService, businessHoursRepository);
    }

    @Test
    public void testGetBusinessHoursForLocation() {
        // Set up test data
        Location location = new Location();
        location.setId(1L);
        location.setName("Location 1");
        location.setAddress("Address 1");
        location.setPhone("1234567890");
        locationRepository.save(location);

        // Set up business hours
        for (int i = 1; i < 8; i++) {
            BusinessHours bh = new BusinessHours();
            bh.setDay(i);
            bh.setOpenTime(LocalTime.of(8, 0));
            bh.setCloseTime(LocalTime.of(22, 0));
            bh.setLocation(location);
            businessHoursRepository.save(bh);
        }

        // Set up mock holiday data
        Holiday holiday = new Holiday();
        holiday.setId(1L);
        holiday.setName("Holiday 1");
        holiday.setDescription("Holiday 1 description");
        holiday.setDate(LocalDate.parse("2022-01-01")); // New Year's Day
        holiday.setOpenTime(LocalTime.parse("09:00"));
        holiday.setCloseTime(LocalTime.parse("13:00"));
        holidayRepository.save(holiday);

        // Call the method being tested
        LocalDate startDate = LocalDate.parse("2022-01-01");
        LocalDate endDate = LocalDate.parse("2022-01-04");
        List<CalendarDay> businessHours = locationService.getCalendar(location, startDate, endDate);

        // Verify the results
        assertEquals(3, businessHours.size());
        assertEquals(LocalDate.parse("2022-01-01"), businessHours.get(0).getDate());
        assertEquals(LocalTime.of(9, 0), businessHours.get(0).getOpenTime());
        assertEquals(LocalTime.of(13, 0), businessHours.get(0).getCloseTime());
        assertEquals(LocalTime.of(8, 0), businessHours.get(1).getOpenTime());
        assertEquals(LocalTime.of(22, 0), businessHours.get(1).getCloseTime());
    }

}