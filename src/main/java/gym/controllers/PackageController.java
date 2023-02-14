package gym.controllers;

import gym.dtos.PackageUserDto;
import gym.model.Package;
import gym.model.User;
import gym.services.PackageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/packages")
@SecurityRequirement(name = "bearer")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping("{id}")
    public Package getPackage(@PathVariable Long id) {
        return packageService.findById(id);
    }

    @GetMapping()
    public List<Package> getPackages() {
        return packageService.findAll();
    }

    @GetMapping("/active")
    public List<Package> getActivePackages() {
        return packageService.findAllActive();
    }

    @GetMapping("/my-packages")
    public List<PackageUserDto> getMyPackages(@AuthenticationPrincipal User user) {
        return packageService.findAllByUser(user);
    }

    @PostMapping()
    public Package createPackage(@RequestBody Package aPackage) {
        return packageService.save(aPackage);
    }

    @PutMapping(value = "/{id}")
    public Package updatePackage(@PathVariable("id") Long id, @RequestBody Package aPackage) {
        return packageService.updatePackage(id, aPackage);
    }

    @PutMapping(value = "/{id}/services")
    public Package updatePackageServices(@PathVariable("id") Long id, @RequestBody List<Long> services) {
        return packageService.updatePackageServices(id, services);
    }

    @PutMapping(value = "/{id}/activate")
    public Package activatePackage(@PathVariable("id") Long id) {
        return packageService.activatePackage(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePackage(@PathVariable("id") Long id) {
        packageService.deletePackage(id);
    }
}
