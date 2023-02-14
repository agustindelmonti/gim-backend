package gym.controllers;

import gym.model.Service;
import gym.services.ServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/services")
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("{id}")
    public Service getService(@PathVariable Long id) {
        return serviceService.findById(id);
    }

    @GetMapping()
    public List<Service> getServices() {
        return serviceService.findAll();
    }

    @PostMapping()
    public Service createService(@RequestBody Service service) {
        return serviceService.save(service);
    }

    @PutMapping(value = "/{id}")
    public Service updateService(@PathVariable("id") Long id, @RequestBody Service service) {
        return serviceService.updateService(id, service);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteService(@PathVariable("id") Long id) {
        serviceService.deleteService(id);
    }
}
