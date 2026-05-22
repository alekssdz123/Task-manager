package firstproject.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import firstproject.demo.model.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    List<User> findByUserId(UUID userId);
    List<User> findByUsername(String username);

}
