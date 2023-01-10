package gym.controllers;

import gym.dtos.BusinessHoursDto;
import gym.dtos.UpdateBusinessHoursDto;
import gym.model.BusinessHours;
import gym.model.Location;
import gym.services.BusinessHoursService;
import gym.services.LocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/locations")
@SecurityRequirement(name = "bearer")
public class LocationController {
    private final LocationService locationService;
    private final BusinessHoursService businessHoursService;

    public LocationController(LocationService locationService,
                              BusinessHoursService businessHoursService) {
        this.locationService = locationService;
        this.businessHoursService = businessHoursService;
    }

    @GetMapping
    public List<Location> getLocations() {
        return locationService.getLocations();
    }

    @GetMapping("/{id}")
    public Location getById(@PathVariable("id") Long id) {
        return locationService.getLocationById(id);
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationService.saveLocation(location);
    }

    @PutMapping(value = "/{id}")
    public Location updateLocation(@PathVariable("id") Long id, @RequestBody Location location) {
        return locationService.updateLocation(id, location);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteLocation(@PathVariable("id") Long id) {
        locationService.deleteLocation(id);
    }

    @DeleteMapping
    public void deleteAllLocationsByIdInBatch(@RequestBody List<Long> ids) {
        locationService.deleteAllLocationsByIdInBatch(ids);
    }

    @GetMapping("/{id}/business-hours")
    public List<BusinessHoursDto> getBusinessHoursForLocation(@PathVariable("id") Long id) {
        return businessHoursService.findByLocationId(id).stream()
                .map(BusinessHoursDto::new)
                .collect(Collectors.toList());
    }

    /**
     * bulk edit business hours for location
     *
     * @param id            - location id
     * @param businessHours - list of business hours for location
     * @return
     */
    @PutMapping("/{id}/business-hours")
    public List<BusinessHours> setBusinessHoursForLocation(
            @PathVariable("id") Long id,
            @RequestBody List<UpdateBusinessHoursDto> businessHours) {
        final Location location = locationService.getLocationById(id);

        location.getBusinessHours().clear();
        var hours = businessHours.stream()
                .map(UpdateBusinessHoursDto::toBusinessHours).collect(Collectors.toSet());
        location.getBusinessHours().addAll(hours);
        return businessHoursService.findByLocationId(id);
    }

}
