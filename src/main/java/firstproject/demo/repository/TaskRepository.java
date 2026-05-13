package firstproject.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import firstproject.demo.model.Task;

public interface TaskRepository extends JpaRepository<Task, UUID>{
    @Modifying
    @Query("UPDATE Task t SET t.completed = true WHERE t.id = :id")
    void markAsCompleted(@Param("id") UUID id);

    List<Task> findByUserId(UUID userId);

}
