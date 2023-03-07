package sai.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sai.ecommerce.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
