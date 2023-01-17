package gym.services;

import gym.model.Service;
import gym.repository.ServiceRepository;
import gym.utils.NotFoundException;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public Service findById(Long id) {
        return serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Service not found"));
    }

    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    public Service create(Service service) {
        return serviceRepository.save(service);
    }

    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    public Service updateService(Long id, Service service) {
        Service serviceToUpdate = findById(id);
        serviceToUpdate.setName(service.getName());
        serviceToUpdate.setPrice(service.getPrice());
        serviceToUpdate.setDuration(service.getDuration());
        return serviceRepository.save(serviceToUpdate);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
    
    public List<Service> findAllById(List<Long> servicesIds) {
        return serviceRepository.findAllById(servicesIds);
    }
}
