package firstproject.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import firstproject.demo.model.Task;

public interface UserRepository extends JpaRepository<Task, UUID>{
    List<Task> findByUserId(UUID userId);
    List<Task> findByUserIdOrderByCompletedAsc(UUID userId);

}
