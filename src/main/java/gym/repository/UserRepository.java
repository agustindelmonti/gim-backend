package gym.repository;

import gym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByNroDoc(String nroDoc);

    Optional<User> findByEmail(String email);

    Optional<User> findByNroDoc(String nroDoc);
    List<User> findAllByNameContainingIgnoreCase(String name);
}
