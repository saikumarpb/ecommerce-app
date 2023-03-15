package sai.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sai.ecommerce.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
  Optional<CartItem> findByCartIdAndProductId(int cartId, int productId);

  Optional<CartItem> deleteById(int id);
}
