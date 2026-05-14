package firstproject.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import firstproject.demo.model.Task;
import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<Task, UUID>{
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.completed = true WHERE t.id = :id")
    void markAsCompleted(@Param("id") UUID id);

    List<Task> findByUserId(UUID userId);
    List<Task> findByUserIdOrderByCompletedAsc(UUID userId);

}
