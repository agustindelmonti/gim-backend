package gym.controllers;

import gym.model.Location;
import gym.services.LocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/locations")
@SecurityRequirement(name = "bearer")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
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
}
