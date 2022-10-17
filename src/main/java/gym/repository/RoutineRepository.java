package gym.repository;

import gym.model.Routine;
import gym.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {

    Page<Routine> findByMember(User member, Pageable pageable);

    Page<Routine> findByCreator(User creator, Pageable pageable);
}
