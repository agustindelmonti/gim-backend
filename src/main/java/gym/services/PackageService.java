package gym.services;

import gym.model.Package;
import gym.repository.PackageRepository;
import gym.utils.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PackageService {
    private final PackageRepository packageRepository;
    private final ServiceService serviceService;

    public PackageService(PackageRepository packageRepository, ServiceService serviceService) {
        this.packageRepository = packageRepository;
        this.serviceService = serviceService;
    }

    public Package findById(Long id) {
        return packageRepository.findById(id).orElseThrow(() -> new NotFoundException("Package not found"));
    }

    public List<Package> findAll() {
        return packageRepository.findAll();
    }

    public Package save(Package aPackage) {
        return packageRepository.save(aPackage);
    }

    public Package create(Package aPackage) {
        return packageRepository.save(aPackage);
    }

    public Package updatePackage(Long id, Package p) {
        Package packageToUpdate = findById(id);
        packageToUpdate.setName(p.getName());
        packageToUpdate.setDescription(p.getDescription());
        packageToUpdate.setPrice(p.getPrice());
        packageToUpdate.setDuration(p.getDuration());
        packageToUpdate.setActive(p.getActive());
        return packageRepository.save(packageToUpdate);
    }

    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }

    public List<Package> findAllActive() {
        return packageRepository.findAllByActive(true);
    }

    public Package activatePackage(Long id) {
        Package packageToActivate = findById(id);
        packageToActivate.setActive(true);
        return packageRepository.save(packageToActivate);
    }

    @Transactional
    public Package updatePackageServices(Long id, List<Long> servicesIds) {
        Package packageToUpdate = findById(id);

        // find all services by id
        var services = serviceService.findAllById(servicesIds);
        packageToUpdate.getServices().clear();
        packageToUpdate.getServices().addAll(services);

        return packageRepository.save(packageToUpdate);
    }
}
