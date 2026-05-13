package firstproject.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import firstproject.demo.model.Task;

public interface TaskRepository extends JpaRepository<Task, UUID>{
    
}
