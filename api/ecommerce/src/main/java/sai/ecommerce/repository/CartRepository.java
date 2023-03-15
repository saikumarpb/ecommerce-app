package sai.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sai.ecommerce.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
  Optional<Cart> findByUserId(int userId);
}
