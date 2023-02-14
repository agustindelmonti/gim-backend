package gym.repository;

import gym.model.PackageUser;
import gym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageUserRepository extends JpaRepository<PackageUser, Long> {
    List<PackageUser> findAllByUser(User user);
}
