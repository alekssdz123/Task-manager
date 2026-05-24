package firstproject.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import firstproject.demo.model.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findByUserId(UUID userId);
    Optional<User> findByUsername(String username);

}
