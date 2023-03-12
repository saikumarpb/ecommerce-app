package sai.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sai.ecommerce.domain.State;

public interface StateRepository extends JpaRepository<State, Integer> {
  Optional<State> findById(int id);
}
